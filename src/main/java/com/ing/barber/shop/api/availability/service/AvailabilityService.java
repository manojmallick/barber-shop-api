package com.ing.barber.shop.api.availability.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.error.ResourceNotFoundException;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  private AppointmentRepository appointmentRepository;
  private ShopRepository shopRepository;
  private BarberRepository barberRepository;
  private BarberShopApiUtil barberShopApiUtil;

  public Map<String, Set<String>> getAllAvailabilityByDate(LocalDate bookingDate, LocalDate endDate,
      String shopId) {
    endDate = endDate == null ? bookingDate : endDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD);
    Optional<Shop> shop = shopRepository.findById(shopId);
    List<Barber> barbers = barberRepository.findAll();

    Set<String> dateSlots = barberShopApiUtil.getFormattedDate(bookingDate.toString(),
        endDate.toString(), BarberShopApiConstants.YYYY_MM_DD, Calendar.DATE, 1);
    List<Appointment> appointments = appointmentRepository
        .findAllAppointmentByBookingDateAndEndDate(bookingDate, endDate);

    if (!shop.isPresent()) {
      throw new ResourceNotFoundException("Shop not found");
    }
    Map<String, Set<String>> dateSetMap = new LinkedHashMap<>();
    dateSlots.stream().forEach(
        dateSlot -> {
          Set<String> finalTimeslots = barberShopApiUtil
              .getTimeSlots(simpleDateFormat, shop.get(), dateSlot);
          List<Appointment> currentAppointment = filterAppointmentsWithDateSlot(
              appointments, dateSlot);
          //size check is enough since unique check is in place
          if (currentAppointment.size() == barbers.size()) {
            finalTimeslots.remove(currentAppointment.get(0).getBookingDate().toString());
          }
          dateSetMap.put(dateSlot, finalTimeslots);
        }
    );
    return dateSetMap;
  }


  private List<Appointment> filterAppointmentsWithDateSlot(List<Appointment> appointments,
      String dateSlot) {
    List<Appointment> currentAppointment = appointments.stream()
        .filter(appointment -> dateSlot
            .equalsIgnoreCase(appointment.getBookingDate().toString()))
        .collect(Collectors.toList());
    return currentAppointment;
  }

}
