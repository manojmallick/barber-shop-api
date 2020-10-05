package com.ing.barber.shop.api.appointment.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.beans.Customer;
import com.ing.barber.shop.api.config.ApplicationProperties;
import com.ing.barber.shop.api.config.ApplicationProperties.ShopDetails;
import com.ing.barber.shop.api.error.ErrorCodes;
import com.ing.barber.shop.api.error.GenericApiException;
import com.ing.barber.shop.api.error.ResourceAlreadyExists;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

  public static final LocalDate BOOKING_DATE = LocalDate.of(2020, 10, 5);
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
    exceptionRule.expectMessage(is(BarberShopApiConstants.START_TIME_IS_INVALID));
    exceptionRule.expect(hasProperty("errorCodes"));
    exceptionRule
        .expect(hasProperty("errorCodes", is(ErrorCodes.ERROR_BOOKING_TIME_NOT_VALID)));

    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }


  @Test
  public void saveAppointmentWithFullyBookedTimeSlot() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10:30");
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> appointments = getBookedAppointments();

    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(appointments);

    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber()));

    //assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.START_TIME_IS_NOT_AVAILABLE));
    exceptionRule.expect(hasProperty("errorCodes"));
    exceptionRule
        .expect(hasProperty("errorCodes", is(ErrorCodes.ERROR_BOOKING_TIME_NOT_AVAILABLE)));

    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }

  @Test
  public void saveAppointmentWithBarberId() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10:30");
    appointmentRequest.setBarber(getBarber());
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> appointments = getBookedAppointments();

    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(appointments);

    // real world there world be different barber
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    //assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule
        .expectMessage(is(BarberShopApiConstants.BOOKING_BY_BARBER_ID_FEATURE_NOT_AVAILABLE));
    exceptionRule.expect(hasProperty("errorCodes"));
    exceptionRule
        .expect(hasProperty("errorCodes", is(ErrorCodes.ERROR_FEATURE_NOT_AVAILABLE)));

    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }

  @Test
  public void saveAppointmentWithSameCustomer() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10:30");
    appointmentRequest.setCustomer(getCustomer());
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> bookedAppointments = getBookedAppointments();
    bookedAppointments.get(0).setCustomer(getCustomer());
    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(bookedAppointments);

    // real world there world be different barber
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    //assert
    exceptionRule.expect(ResourceAlreadyExists.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.BOOKING_ALREADY_EXIST_FOR_THE_CUSTOMER));
    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }

  //real scenario it would not be happening
  @Test
  public void saveAppointmentWithInvalidStartTimeFormat() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10");
    appointmentRequest.setCustomer(getCustomer());
    Set<String> timeSlots = getInvalidTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> bookedAppointments = getBookedAppointments();
    Customer customer = getCustomer();
    customer.setEmail("new@test.com");
    bookedAppointments.get(0).setCustomer(customer);
    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(bookedAppointments);

    // real world there world be different barber
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    //assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.ERROR_WHILE_RETRIEVING_END_TIME));
    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }

  @Test
  public void saveAppointmentWithNoAvailableBarbers() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10:30");
    appointmentRequest.setCustomer(getCustomer());
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> bookedAppointments = getBookedAppointments();
    Customer customer = getNewCustomer("new@test.com");
    bookedAppointments.get(0).setCustomer(customer);
    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(bookedAppointments);

    when(applicationProperties.getShopDetails()).thenReturn(getShopDetails());
    // real world there world be different barber
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    //assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.NO_BARBER_AVAILABLE_FOR_BOOKING));
    //method call
    appointmentService.saveAppointment(appointmentRequest);
  }

  @Test
  public void saveAppointmentWithAvailableBarbers() {
    //input
    Appointment appointmentRequest = getAppointment();
    appointmentRequest.setStartTime("10:30");
    appointmentRequest.setCustomer(getCustomer());
    Set<String> timeSlots = getTimeSlots();

    //mock
    when(shopRepository.findById(anyString()))
        .thenReturn(Optional.of(appointmentRequest.getShop()));
    when(barberShopApiUtil.getTimeSlots(any(SimpleDateFormat.class), any(Shop.class), anyString()))
        .thenReturn(
            timeSlots);
    List<Appointment> bookedAppointments = getBookedAppointments();
    Customer customer = getNewCustomer("new@test.com");
    bookedAppointments.get(0).setCustomer(customer);
    when(appointmentRepository
        .findAllAppointmentByBookingDateAndStartTime(any(LocalDate.class), anyString()))
        .thenReturn(bookedAppointments);

    when(applicationProperties.getShopDetails()).thenReturn(getShopDetails());
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getNewBarber("2")));
    Appointment appointment = getAppointment();
    appointment.setId("1");
    when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

    //method call
    Appointment actualAppointment = appointmentService.saveAppointment(appointmentRequest);

    //assert
    assertNotNull(actualAppointment);
    assertThat(actualAppointment.getId(), is(appointment.getId()));

  }

  private Barber getNewBarber(String s) {
    Barber newBarber = new Barber();
    newBarber.setId(s);
    return newBarber;
  }

  private Customer getNewCustomer(String s) {
    Customer customer = new Customer();
    customer.setEmail(s);
    return customer;
  }

  private ShopDetails getShopDetails() {
    ShopDetails shopDetails = new ShopDetails();
    shopDetails.setEndTime(30);
    return shopDetails;
  }

  private Customer getCustomer() {
    Customer customer = getNewCustomer("test@test.com");
    return customer;
  }

  private List<Appointment> getBookedAppointments() {
    Appointment mockAppointment = getAppointment();
    Barber mockBarber = getBarber();
    mockAppointment.setBarber(mockBarber);
    List<Appointment> appointments = Arrays.asList(mockAppointment);
    return appointments;
  }

  private Barber getBarber() {
    Barber mockBarber = getNewBarber("1");
    return mockBarber;
  }

  private Set<String> getTimeSlots() {
    String[] time = {"10:00", "10:30", "11:00", "12:00"};
    Set<String> timeSlots = Arrays.stream(time).collect(Collectors.toSet());
    return timeSlots;
  }

  private Set<String> getInvalidTimeSlots() {
    String[] time = {"10"};
    Set<String> timeSlots = Arrays.stream(time).collect(Collectors.toSet());
    return timeSlots;
  }

  private Appointment getAppointment() {
    Appointment appointmentRequest = new Appointment();
    appointmentRequest.setStartTime("08:30");
    Shop shop = new Shop();
    shop.setId("1");
    appointmentRequest.setBookingDate(BOOKING_DATE);
    appointmentRequest.setShop(shop);
    return appointmentRequest;
  }

}
