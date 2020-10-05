package com.ing.barber.shop.api.availability;

import com.ing.barber.shop.api.availability.service.AvailabilityService;
import com.ing.barber.shop.api.validators.ValidShop;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/availabilities")
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityController {

  private AvailabilityService availabilityService;

  @GetMapping(
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public Map<String, Set<String>> getAllAvailabilityByDate(
      @Valid @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent LocalDate bookingDate,
      @Valid @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent LocalDate endDate,
      @RequestParam() @ValidShop String shopId
      , HttpServletRequest httpRequest)
      throws ParseException {
    log.info("Made request to get All Availability ByDate API. [url={}]",
        httpRequest.getRequestURI());
    return availabilityService.getAllAvailabilityByDate(bookingDate, endDate, shopId);
  }
}