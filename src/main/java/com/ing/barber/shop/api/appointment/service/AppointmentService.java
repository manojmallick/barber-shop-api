package com.ing.barber.shop.api.appointment.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.config.ApplicationProperties;
import com.ing.barber.shop.api.error.ErrorCodes;
import com.ing.barber.shop.api.error.GenericApiException;
import com.ing.barber.shop.api.error.ResourceAlreadyExists;
import com.ing.barber.shop.api.error.ResourceNotFoundException;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The type Appointment service.
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final BarberRepository barberRepository;
  private final ApplicationProperties applicationProperties;
  private final BarberShopApiUtil barberShopApiUtil;
  private final ShopRepository shopRepository;

  /**
   * Save appointment appointment response.
   *
   * @param appointment the appointment
   * @return the appointment response
   * @throws GenericApiException the generic api exception
   */
  @Transactional
  public Appointment saveAppointment(Appointment appointment) throws GenericApiException {
    //Check if the startTime available with not present(considering)

    Shop shop = shopRepository.findById(appointment.getShop().getId()).orElseThrow(
        () -> new ResourceNotFoundException(BarberShopApiConstants.SHOP_NOT_FOUND,
            ErrorCodes.ERROR_SHOP_NOT_FOUND));

    Set<String> slots = barberShopApiUtil
        .getTimeSlots(new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD), shop,
            appointment.getBookingDate().toString());

    if (isStartTimeInValid(appointment, slots)) {
      throw new GenericApiException(BarberShopApiConstants.START_TIME_IS_INVALID,
          HttpStatus.BAD_REQUEST,
          ErrorCodes.ERROR_BOOKING_TIME_NOT_VALID);
    }
    List<Appointment> appointments = appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(appointment.getBookingDate(),
            appointment.getStartTime());

    List<Barber> barbers = barberRepository.findAll();
    if (appointments.size() == barbers.size()) {
      throw new GenericApiException(BarberShopApiConstants.START_TIME_IS_NOT_AVAILABLE,
          HttpStatus.BAD_REQUEST,
          ErrorCodes.ERROR_BOOKING_TIME_NOT_AVAILABLE);
    }

    if (appointment.getBarber() != null && !StringUtils.isEmpty(appointment.getBarber().getId())) {
      throw new GenericApiException(
          BarberShopApiConstants.BOOKING_BY_BARBER_ID_FEATURE_NOT_AVAILABLE,
          HttpStatus.BAD_REQUEST, ErrorCodes.ERROR_FEATURE_NOT_AVAILABLE);
    }

    if (isBookingExistWithCustomer(appointment, appointments)) {
      throw new ResourceAlreadyExists(
          BarberShopApiConstants.BOOKING_ALREADY_EXIST_FOR_THE_CUSTOMER,
          ErrorCodes.ERROR_DUPLICATE_BOOKING_CUSTOMER);
    }

    List<Barber> availableBarbers = getAvailableBarbers(
        appointments, barbers);

    String endTime = mapEndTime(appointment);

    if (availableBarbers.size() > 0) {
      appointment.setBarber(availableBarbers.get(0));
      appointment.setEndTime(endTime);
    } else {
      throw new GenericApiException(BarberShopApiConstants.NO_BARBER_AVAILABLE_FOR_BOOKING,
          HttpStatus.BAD_REQUEST, ErrorCodes.ERROR_FEATURE_NOT_AVAILABLE);
    }
    return appointmentRepository.save(appointment);
  }

  private List<Barber> getAvailableBarbers(List<Appointment> appointments, List<Barber> barbers) {
    return barbers.stream().filter(
        barber -> appointments.stream().filter(
            appointment -> appointment.getBarber().getId().equalsIgnoreCase(barber.getId()))
            .count() == 0
    ).collect(Collectors.toList());
  }

  private boolean isStartTimeInValid(Appointment appointment, Set<String> slots) {
    return slots.stream().filter(s -> s.equalsIgnoreCase(appointment.getStartTime())).count() == 0;
  }

  private boolean isBookingExistWithCustomer(Appointment appointment,
      List<Appointment> appointments) {
    return appointments.stream().filter(existingAppointment ->
        appointment.getCustomer().getEmail()
            .equalsIgnoreCase(existingAppointment.getCustomer().getEmail())
    ).count() > 0;
  }

  private String mapEndTime(Appointment appointment) {
    try {
      Calendar calendar = GregorianCalendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat(BarberShopApiConstants.HH_MM);
      Date endDate = sdf.parse(appointment.getStartTime());
      calendar.setTime(endDate);
      calendar.add(Calendar.MINUTE, applicationProperties.getShopDetails().getEndTime());
      return sdf.format(calendar.getTime());
    } catch (ParseException e) {
      throw new GenericApiException(BarberShopApiConstants.ERROR_WHILE_RETRIEVING_END_TIME,
          HttpStatus.BAD_REQUEST, ErrorCodes.ERROR_WHILE_PARSING);
    }
  }
  public List<Appointment> getAllAppointments(){
    return appointmentRepository.findAll();
  }


  public Appointment getAppointment( String id ){
    return appointmentRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException(BarberShopApiConstants.APPOINTMENT_NOT_FOUND,
        ErrorCodes.ERROR_APPOINTMENT_NOT_FOUND));
  }


}
