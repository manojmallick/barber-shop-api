package com.ing.barber.shop.api.shop;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.service.ShopService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/shops")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ShopController {

  private ShopService shopService;

  @GetMapping(
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<Shop> getAllShops(HttpServletRequest httpRequest) {

    log.info("Made request to get All Shops. [url={}]", httpRequest.getRequestURI());
    return shopService.getAllShops();
  }

}
