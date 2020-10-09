package com.ing.barber.shop.api.scheduler;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.email.EmailService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduledTasks {

  public static final int HOUR = 1 * 60 * 60 * 1000;
  public static final int MINUTES = 60 * 1000;
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
  private final AppointmentRepository appointmentRepository;
  private final EmailService emailService;

  @Scheduled(cron = "0 0/30 8-17 * * MON-FRI") // every 30 minutes 9am - 5pm weekdays
  @Scheduled(cron = "0 0/30 11-16 * * SUN-SAT") // every 30 minutes 12pm - 4pm weekend
  public void reportCurrentTime() {
    log.debug("The time is now {}", dateFormat.format(new Date()));

    LocalDate bookingDate = LocalDate.now();
    String currentTime = dateFormat.format(new Date());
    System.out.println(currentTime);
    List<Appointment> appointments =
        appointmentRepository.findAllAppointmentByBookingDateAndEndDate(bookingDate, bookingDate);
    appointments.forEach(
        appointment -> {
          try {
            Date booingTime = dateFormat.parse(appointment.getStartTime());
            Date currentTimeParsed = dateFormat.parse(currentTime);

            long difference = booingTime.getTime() - currentTimeParsed.getTime();
            if (difference > 0 && difference <= HOUR) {
              emailService.sendReminderEmail(appointment, difference / MINUTES);
            }
          } catch (ParseException e) {
            log.error("failure while sending reminder "+e.getMessage());
          }
        });
  }
}
