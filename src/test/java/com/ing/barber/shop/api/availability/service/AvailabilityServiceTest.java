package com.ing.barber.shop.api.availability.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.availability.json.Availability;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.error.ResourceNotFoundException;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import com.ing.barber.shop.api.util.BarberShopApiUtil;
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

/** The type Availability service test. */
@RunWith(MockitoJUnitRunner.class)
public class AvailabilityServiceTest {

  public static final LocalDate BOOKING_DATE = LocalDate.of(2020, 10, 5);
  public static final String SHOP_ID = "1";
  /** The Exception rule. */
  @Rule public ExpectedException exceptionRule = ExpectedException.none();

  @Mock private AppointmentRepository appointmentRepository;
  @Mock private BarberRepository barberRepository;
  @Mock private BarberShopApiUtil barberShopApiUtil;
  @Mock private ShopRepository shopRepository;

  @InjectMocks private AvailabilityService availabilityService;

  /** Gets all availability by date non existing shop. */
  @Test
  public void getAllAvailabilityByDateNonExistingShop() {
    // mock
    when(shopRepository.findById(anyString())).thenReturn(Optional.empty());

    // assert
    exceptionRule.expect(ResourceNotFoundException.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.SHOP_NOT_FOUND));
    // method call
    availabilityService.getAllAvailabilityByDate(BOOKING_DATE, BOOKING_DATE, SHOP_ID);
  }

  /** Gets all availability by date existing shop and same date range. */
  @Test
  public void getAllAvailabilityByDateExistingShopAndSameDateRange() {
    // mock
    when(shopRepository.findById(anyString())).thenReturn(Optional.of(getShop()));
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    when(barberShopApiUtil.getFormattedDate(anyString(), anyString(), any(), anyInt(), anyInt()))
        .thenReturn(getDateSlots());

    when(appointmentRepository.findAllAppointmentByBookingDateAndEndDate(
            BOOKING_DATE, BOOKING_DATE))
        .thenReturn(getBookedAppointments());

    when(barberShopApiUtil.getTimeSlots(any(), any(), any())).thenReturn(getTimeSlots());

    // method call
    List<Availability> actualDateTimeSlotMap =
        availabilityService.getAllAvailabilityByDate(BOOKING_DATE, BOOKING_DATE, SHOP_ID);

    assertNotNull(actualDateTimeSlotMap);
    for (Availability entry : actualDateTimeSlotMap) {
      assertThat(entry.getId(), is(BOOKING_DATE.toString()));
      assertThat(entry.getTimeSlot(), is(getTimeSlots()));
    }
  }

  private Barber getNewBarber(String s) {
    Barber newBarber = new Barber();
    newBarber.setId(s);
    return newBarber;
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

  private Set<String> getDateSlots() {
    String[] date = {"2020-10-05"};
    Set<String> dateSlots = Arrays.stream(date).collect(Collectors.toSet());
    return dateSlots;
  }

  private Appointment getAppointment() {
    Appointment appointmentRequest = new Appointment();
    appointmentRequest.setId("1");
    appointmentRequest.setStartTime("08:30");
    Shop shop = getShop();
    appointmentRequest.setBookingDate(BOOKING_DATE);
    appointmentRequest.setShop(shop);
    return appointmentRequest;
  }

  private Shop getShop() {
    Shop shop = new Shop();
    shop.setId("1");
    return shop;
  }
}
