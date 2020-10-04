package com.ing.barber.shop.api.services;

import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.services.service.ServicesService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/services")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ServicesController {

  private ServicesService servicesService;

  @GetMapping
  public List<Service> getAllServices() {
    return servicesService.getAllServices();
  }
}
