package com.ing.barber.shop.api.barber.service;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The type Barber service. */
@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberService {

  private final BarberRepository barberRepository;

  /**
   * Gets all barbers.
   *
   * @return the all barbers
   */
  public List<Barber> getAllBarbers() {
    return barberRepository.findAll();
  }
}
