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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
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
    List<Availability> availabilities = new ArrayList<Availability>();
    dateSlots.stream()
        .forEach(
            dateSlot -> {
              Set<String> finalTimeslots =
                  barberShopApiUtil.getTimeSlots(simpleDateFormat, shop, dateSlot);
              List<Appointment> currentAppointment =
                  filterAppointmentsWithDateSlot(appointments, dateSlot);

              removeTimeSlotsIfBooked(barbers, finalTimeslots, currentAppointment);
              availabilities.add(new Availability(dateSlot, finalTimeslots));
            });

    return availabilities;
  }

  private void removeTimeSlotsIfBooked(
      List<Barber> barbers, Set<String> finalTimeslots, List<Appointment> currentAppointment) {
    if (currentAppointment.size() == barbers.size()) {
      finalTimeslots.remove(currentAppointment.get(0).getBookingDate().toString());
    }
  }

  private List<Appointment> filterAppointmentsWithDateSlot(
      List<Appointment> appointments, String dateSlot) {
    List<Appointment> currentAppointment =
        appointments.stream()
            .filter(
                appointment -> dateSlot.equalsIgnoreCase(appointment.getBookingDate().toString()))
            .collect(Collectors.toList());
    return currentAppointment;
  }
}
