package com.ing.barber.shop.api.barber;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.service.BarberService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Barber controller.
 */
@RestController
@RequestMapping("/v1/barbers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class BarberController {

  private final BarberService barberService;

  /**
   * Gets all barbers.
   *
   * @param httpRequest the http request
   * @return the all barbers
   */
  @GetMapping
  public List<Barber> getAllBarbers(HttpServletRequest httpRequest) {

    log.info("Made request to get All Barbers API. [url={}]", httpRequest.getRequestURI());
    return barberService.getAllBarbers();
  }

}
