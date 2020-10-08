package com.ing.barber.shop.api.shop.service;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The type Shop service. */
@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ShopService {

  private final ShopRepository shopRepository;

  /**
   * Gets all shops.
   *
   * @return the all shops
   */
  public List<Shop> getAllShops() {
    return shopRepository.findAll();
  }
}
