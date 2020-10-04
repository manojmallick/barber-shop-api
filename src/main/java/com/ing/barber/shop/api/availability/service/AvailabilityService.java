package com.ing.barber.shop.api.availability.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.beans.Day;
import com.ing.barber.shop.api.beans.Schedule;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityService {

  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String HH_MM = "HH:mm";
  private AppointmentRepository appointmentRepository;
  private ShopRepository shopRepository;

  public Map<String, Set<String>> getAllAvailabilityByDate(Date bookingDate, Date endDate)
      throws ParseException {
    endDate = endDate == null ? bookingDate : endDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
    Shop shop = shopRepository.findAll().get(0);
    Set<String> dateSlots = getFormattedDate(simpleDateFormat.format(bookingDate),
        simpleDateFormat.format(endDate), YYYY_MM_DD, Calendar.DATE, 1);
    List<Appointment> appointments = appointmentRepository
        .findAllAppointmentByScheduleDayOfWeek(bookingDate, endDate);

    Map<String, Set<String>> dateSetMap = new LinkedHashMap<>();
    dateSlots.stream().forEach(
        dateSlot -> {
          Set<String> finalTimeslots = getTimeSlots(simpleDateFormat, shop, dateSlot);
          appointments.stream().filter(appointment -> dateSlot
              .equalsIgnoreCase(simpleDateFormat.format(appointment.getBookingDate()))).forEach(
              appointment -> {
                finalTimeslots.remove(appointment.getStartTime());
              }
          );
          dateSetMap.put(dateSlot, finalTimeslots);
        }
    );
    return dateSetMap;

  }


  private Set<String> getTimeSlots(SimpleDateFormat simpleDateFormat, Shop shop, String dateSlot) {
    Set<String> timeSlots = null;
    try {
      Calendar c = GregorianCalendar.getInstance();
      c.setTime(simpleDateFormat.parse(dateSlot));
      int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

      List<Schedule> schedules = shop.getSchedules()
          .stream()
          .filter(s -> s.getDayOfWeek().equalsIgnoreCase(Day.values()[dayOfWeek - 1].toString()))
          .collect(Collectors.toList());
      timeSlots = getFormattedDate(schedules.get(0).getStartTime(),
          schedules.get(0).getEndTime(), HH_MM, Calendar.MINUTE, 30);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return timeSlots;
  }

  private Set<String> getFormattedDate(String startTime, String endTime, String format,
      int calendarType, int slot) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date startDate = sdf.parse(startTime);
    Date endDate = sdf.parse(endTime);

    Set<String> range = new LinkedHashSet<>();
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(startDate);

    if (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
      range.add(sdf.format(calendar.getTime()));
      while (calendar.getTime().before(endDate)) {
        calendar.add(calendarType, slot);
        range.add(sdf.format(calendar.getTime()));
      }
    }
    return range;
  }
}
