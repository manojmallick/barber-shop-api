package com.ing.barber.shop.api.shop.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest {

  @Mock private ShopRepository shopRepository;

  @InjectMocks private ShopService shopService;

  @Test
  public void getAllServices() {
    // mock
    when(shopRepository.findAll()).thenReturn(Arrays.asList(getShop(), getShop()));

    List<Shop> services = shopService.getAllShops();
    assertNotNull(services);
  }

  private Shop getShop() {
    Shop shop = new Shop();
    shop.setId("1");
    return shop;
  }
}
