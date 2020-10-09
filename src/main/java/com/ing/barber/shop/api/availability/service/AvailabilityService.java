package com.ing.barber.shop.api.availability.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.availability.json.Availability;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.error.ErrorCodes;
import com.ing.barber.shop.api.error.ResourceNotFoundException;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The type Availability service. */
@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityService {

  private final AppointmentRepository appointmentRepository;
  private final ShopRepository shopRepository;
  private final BarberRepository barberRepository;
  private final BarberShopApiUtil barberShopApiUtil;

  /**
   * Gets all availability by date.
   *
   * @param bookingDate the booking date
   * @param endDate the end date
   * @param shopId the shop id
   * @return the all availability by date
   */
  public List<Availability> getAllAvailabilityByDate(
      final LocalDate bookingDate, final LocalDate endDate, final String shopId) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD);
    Shop shop =
        shopRepository
            .findById(shopId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        BarberShopApiConstants.SHOP_NOT_FOUND, ErrorCodes.ERROR_SHOP_NOT_FOUND));
    List<Barber> barbers = barberRepository.findAll();

    Set<String> dateSlots =
        barberShopApiUtil.getFormattedDate(
            bookingDate.toString(),
            endDate.toString(),
            BarberShopApiConstants.YYYY_MM_DD,
            Calendar.DATE,
            1);
    List<Appointment> appointments =
        appointmentRepository.findAllAppointmentByBookingDateAndEndDate(bookingDate, endDate);
    List<Availability> availabilities = new LinkedList<>();

    dateSlots.stream()
        .forEach(
            dateSlot -> {
              Map<String, List<String>> barberTimeSlot = new LinkedHashMap<>();
              barbers.forEach(
                  barber -> {
                    barberTimeSlot.put(barber.getId(), new LinkedList<>());
                  });
              final Set<String> finalTimeslots =
                  barberShopApiUtil.getTimeSlots(simpleDateFormat, shop, dateSlot);
              final Set<String> slot = new TreeSet<>();
              mapBarbersTimeSlot(
                  barbers, appointments, dateSlot, barberTimeSlot, finalTimeslots, slot);

              availabilities.add(new Availability(dateSlot, slot, barberTimeSlot));
            });

    return availabilities;
  }

  private void mapBarbersTimeSlot(
      List<Barber> barbers,
      List<Appointment> appointments,
      String dateSlot,
      Map<String, List<String>> barberTimeSlot,
      Set<String> finalTimeslots,
      Set<String> slot) {
    finalTimeslots.forEach(
        time -> {
          List<Appointment> currentAppointment =
              filterAppointmentsWithDateSlot(appointments, dateSlot, time);
          SimpleDateFormat simpleDateFormat =
              new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD);
          SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(BarberShopApiConstants.HH_MM);
          Date date = new Date();
          String todayDate = simpleDateFormat.format(date);
          String currentTime = simpleTimeFormat.format(date);
          if (currentAppointment.size() != barbers.size()) {
            // check if the date is today and time

            if (!todayDate.equalsIgnoreCase(dateSlot)) {
              if (barberShopApiUtil.isValidTimeSlot(currentTime, time)) {
                slot.add(time);
              }
            } else {
              slot.add(time);
            }
          }
          barbers.forEach(
              barber -> {
                if (currentAppointment.stream()
                        .filter(
                            appointment ->
                                appointment.getBarber().getId().equalsIgnoreCase(barber.getId()))
                        .count()
                    == 0) {
                  if (!todayDate.equalsIgnoreCase(dateSlot)) {
                    if (barberShopApiUtil.isValidTimeSlot(currentTime, time)) {
                      barberTimeSlot.get(barber.getId()).add(time);
                    }
                  } else {
                    barberTimeSlot.get(barber.getId()).add(time);
                  }
                }
              });
        });
  }

  private List<Appointment> filterAppointmentsWithDateSlot(
      List<Appointment> appointments, String dateSlot, String timeslot) {
    List<Appointment> currentAppointment =
        appointments.stream()
            .filter(
                appointment ->
                    dateSlot.equalsIgnoreCase(appointment.getBookingDate().toString())
                        && timeslot.equalsIgnoreCase(appointment.getStartTime()))
            .collect(Collectors.toList());
    return currentAppointment;
  }
}
