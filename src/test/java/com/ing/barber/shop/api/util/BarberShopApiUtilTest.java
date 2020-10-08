package com.ing.barber.shop.api.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.beans.Schedule;
import com.ing.barber.shop.api.config.ApplicationProperties;
import com.ing.barber.shop.api.config.ApplicationProperties.ShopDetails;
import com.ing.barber.shop.api.error.GenericApiException;
import com.ing.barber.shop.api.shop.model.Shop;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
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
public class BarberShopApiUtilTest {

  public static final LocalDate BOOKING_DATE = LocalDate.of(2020, 10, 5);
  public static final String SHOP_ID = "1";
  /** The Exception rule. */
  @Rule public ExpectedException exceptionRule = ExpectedException.none();

  @Mock ApplicationProperties applicationProperties;

  @InjectMocks private BarberShopApiUtil barberShopApiUtil;

  /** Gets all availability by date non existing shop. */
  @Test
  public void getTimeSlotsWithValidInput() {

    ShopDetails shopDetails = new ShopDetails();
    shopDetails.setEndTime(30);
    // mock
    when(applicationProperties.getShopDetails()).thenReturn(shopDetails);
    // assert

    // method call
    Set<String> slotSet =
        barberShopApiUtil.getTimeSlots(
            new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD), getShop(), "2020-10-08");

    assertNotNull(slotSet);
    assertThat(slotSet.size(), is(4));
    assertThat(slotSet, is(equalTo(getTimeSlots())));
  }

  @Test
  public void getTimeSlotsWithInValidDate() {

    ShopDetails shopDetails = new ShopDetails();
    shopDetails.setEndTime(100);
    // assert
    exceptionRule.expect(GenericApiException.class);
    exceptionRule.expectMessage(is(BarberShopApiConstants.ERROR_WHILE_PARSING_TIME_SLOTS));

    // method call
    barberShopApiUtil.getTimeSlots(
        new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD), getShop(), "abcd");
  }

  @Test
  public void getTimeSlotsWithInValidEndTime() {

    ShopDetails shopDetails = new ShopDetails();
    shopDetails.setEndTime(100);
    // mock
    // assert
    exceptionRule.expect(GenericApiException.class);

    Shop shop = getShop();
    shop.getSchedules().get(0).setEndTime("8888");
    // method call
    barberShopApiUtil.getTimeSlots(
        new SimpleDateFormat(BarberShopApiConstants.YYYY_MM_DD), shop, "2020-10-08");
  }

  private Barber getNewBarber(String s) {
    Barber newBarber = new Barber();
    newBarber.setId(s);
    return newBarber;
  }

  private Set<String> getTimeSlots() {
    String[] time = {"10:00", "10:30", "11:00", "11:30"};
    Set<String> timeSlots = Arrays.stream(time).collect(Collectors.toSet());
    return timeSlots;
  }

  private Shop getShop() {
    Shop shop = new Shop();
    shop.setId("1");

    Schedule schedule = new Schedule();
    schedule.setDayOfWeek("THURSDAY");
    schedule.setStartTime("10:00");
    schedule.setEndTime("12:00");

    shop.setSchedules(Arrays.asList(schedule));
    return shop;
  }
}
