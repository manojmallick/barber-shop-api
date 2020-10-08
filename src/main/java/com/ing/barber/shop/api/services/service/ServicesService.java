package com.ing.barber.shop.api.services.service;

import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.services.repo.ServicesRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/** The type Services service. */
@org.springframework.stereotype.Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ServicesService {

  private final ServicesRepository servicesRepository;

  /**
   * Gets all services.
   *
   * @return the all services
   */
  @Transactional
  public List<Service> getAllServices() {
    return servicesRepository.findAll();
  }
}
