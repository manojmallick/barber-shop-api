package com.ing.barber.shop.api.appointment.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.config.ApplicationProperties;
import com.ing.barber.shop.api.error.ErrorCodes;
import com.ing.barber.shop.api.error.GenericApiException;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();
  @Mock
  private AppointmentRepository appointmentRepository;
  @Mock
  private BarberRepository barberRepository;
  @Mock
  private ApplicationProperties applicationProperties;
  @Mock
  private BarberShopApiUtil barberShopApiUtil;
  @Mock
  private ShopRepository shopRepository;

  @InjectMocks
  private AppointmentService appointmentService;


  @Test
  public void saveAppointmentWithInvalidStartTime() {
    //input
    Appointment appointmentRequest = getAppointment();
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);

    //assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule.expectMessage(is("Start time is invalid"));
    exceptionRule.expect(hasProperty("errorCodes"));
    exceptionRule
        .expect(hasProperty("errorCodes", is(ErrorCodes.ERROR_BOOKING_TIME_NOT_AVAILABLE)));

    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }


  private Set<String> getTimeSlots() {
    String[] time = {"10:00", "10:30", "11:00", "12:00"};
    Set<String> timeSlots = Arrays.stream(time).collect(Collectors.toSet());
    return timeSlots;
  }


  private Appointment getAppointment() {
    Appointment appointmentRequest = new Appointment();
    appointmentRequest.setStartTime("08:30");
    Shop shop = new Shop();
    shop.setId("1");
    appointmentRequest.setBookingDate(LocalDate.of(2020, 10, 5));
    appointmentRequest.setShop(shop);
    return appointmentRequest;
  }

}
