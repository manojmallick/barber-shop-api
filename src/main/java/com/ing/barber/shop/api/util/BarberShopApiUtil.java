package com.ing.barber.shop.api.util;

import com.ing.barber.shop.api.beans.Day;
import com.ing.barber.shop.api.beans.Schedule;
import com.ing.barber.shop.api.config.ApplicationProperties;
import com.ing.barber.shop.api.error.ErrorCodes;
import com.ing.barber.shop.api.error.GenericApiException;
import com.ing.barber.shop.api.shop.model.Shop;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/** The type Barber shop api util. */
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberShopApiUtil {

  private final ApplicationProperties applicationProperties;

  /**
   * Gets time slots.
   *
   * @param simpleDateFormat the simple date format
   * @param shop the shop
   * @param slot the slot(can be time or date)
   * @return the time slots
   */
  public Set<String> getTimeSlots(SimpleDateFormat simpleDateFormat, Shop shop, String slot) {
    Set<String> timeSlots = null;
    try {
      Calendar c = GregorianCalendar.getInstance();
      c.setTime(simpleDateFormat.parse(slot));
      int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

      List<Schedule> schedules =
          shop.getSchedules().stream()
              .filter(
                  s -> s.getDayOfWeek().equalsIgnoreCase(Day.values()[dayOfWeek - 1].toString()))
              .collect(Collectors.toList());
      String lastBookingSlot = getLastBookingSlot(schedules);
      timeSlots =
          getFormattedDate(
              schedules.get(0).getStartTime(),
              lastBookingSlot,
              BarberShopApiConstants.HH_MM,
              Calendar.MINUTE,
              applicationProperties.getShopDetails().getEndTime());
    } catch (ParseException e) {
      throw new GenericApiException(
          BarberShopApiConstants.ERROR_WHILE_PARSING_TIME_SLOTS,
          HttpStatus.INTERNAL_SERVER_ERROR,
          ErrorCodes.ERROR_WHILE_PARSING,
          e);
    }
    return timeSlots;
  }

  private String getLastBookingSlot(List<Schedule> schedules) {
    Calendar calendar = GregorianCalendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(BarberShopApiConstants.HH_MM);
    Date endDate = null;
    try {
      endDate = sdf.parse(schedules.get(0).getEndTime());
    } catch (ParseException e) {
      throw new GenericApiException(
          BarberShopApiConstants.ERROR_WHILE_RETRIEVING_LAST_BOOKING_SLOTS,
          HttpStatus.BAD_REQUEST,
          ErrorCodes.ERROR_WHILE_PARSING);
    }
    calendar.setTime(endDate);
    calendar.add(Calendar.MINUTE, -applicationProperties.getShopDetails().getEndTime());
    String lastBookingSlot = sdf.format(calendar.getTime());
    return lastBookingSlot;
  }

  /**
   * Gets formatted date.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @param format the format
   * @param calendarType the calendar type
   * @param slot the slot
   * @return the formatted date
   */
  public Set<String> getFormattedDate(
      String startTime, String endTime, String format, int calendarType, int slot) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date startDate = null;
    Date endDate = null;
    try {
      startDate = sdf.parse(startTime);
      endDate = sdf.parse(endTime);
    } catch (ParseException e) {
      throw new GenericApiException(
          BarberShopApiConstants.ERROR_WHILE_PARSING_START_TIME_END_TIME,
          HttpStatus.BAD_REQUEST,
          ErrorCodes.ERROR_WHILE_PARSING,
          e);
    }

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

  /**
   * This will check if time slot is as already passed or not (to be called only for current date)
   *
   * @param CurrentTime the current time
   * @param availableTimeSlot the available time slot
   * @return the boolean
   */
  public boolean isValidTimeSlot(String CurrentTime, String availableTimeSlot) {
    SimpleDateFormat sdf = new SimpleDateFormat(BarberShopApiConstants.HH_MM);
    Date currentDate = null;
    Date availableDate = null;
    try {
      currentDate = sdf.parse(CurrentTime);
      availableDate = sdf.parse(availableTimeSlot);
    } catch (ParseException e) {
      throw new GenericApiException(
          BarberShopApiConstants.ERROR_WHILE_PARSING_START_TIME_END_TIME,
          HttpStatus.BAD_REQUEST,
          ErrorCodes.ERROR_WHILE_PARSING,
          e);
    }
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(currentDate);

    return calendar.getTime().before(availableDate);


  }
}
