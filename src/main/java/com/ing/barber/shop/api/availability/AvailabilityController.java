package com.ing.barber.shop.api.availability;

import com.ing.barber.shop.api.availability.service.AvailabilityService;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/availabilities")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityController {

  private AvailabilityService availabilityService;

  @GetMapping
  public Map<String, Set<String>> getAllAvailabilityByDate(
      @RequestParam()  @DateTimeFormat(pattern = "yyyy-MM-dd")Date bookingDate,
      @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)
      throws ParseException {
    return availabilityService.getAllAvailabilityByDate(bookingDate, endDate);
  }
}