package com.ing.barber.shop.api.services;

import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.services.service.ServicesService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Services controller. */
@RestController
@RequestMapping("/v1/services")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ServicesController {

  private final ServicesService servicesService;

  /**
   * Gets all services.
   *
   * @param httpRequest the http request
   * @return the all services
   */
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<Service> getAllServices(HttpServletRequest httpRequest) {

    log.info("Made request to get All Services API. [url={}]", httpRequest.getRequestURI());
    return servicesService.getAllServices();
  }
}
