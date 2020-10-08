package com.ing.barber.shop.api.availability;

import com.ing.barber.shop.api.availability.json.Availability;
import com.ing.barber.shop.api.availability.service.AvailabilityService;
import com.ing.barber.shop.api.validators.ValidShop;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Availability controller.
 */
@RestController
@RequestMapping("/v1/availabilities")
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityController {

  private final AvailabilityService availabilityService;

  /**
   * Gets all availability by date.
   *
   * @param bookingDate the booking date
   * @param endDate     the end date
   * @param shopId      the shop id
   * @param httpRequest the http request
   * @return the all availability by date
   * @throws ParseException the parse exception
   */
  @GetMapping
  public List<Availability> getAllAvailabilityByDate(
      @Valid @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent final LocalDate bookingDate,
      @Valid @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent final LocalDate endDate,
      @RequestParam() @ValidShop final String shopId, HttpServletRequest httpRequest)
      throws ParseException {
    log.info("Made request to get All Availability ByDate API. [url={}]",
        httpRequest.getRequestURI());
    return availabilityService.getAllAvailabilityByDate(bookingDate, endDate, shopId);
  }
}