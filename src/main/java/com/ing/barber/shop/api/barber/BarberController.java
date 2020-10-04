package com.ing.barber.shop.api.barber;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.service.BarberService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/barbers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberController {

  private BarberService barberService;

  @GetMapping
  public List<Barber> getAllBarbers() {
    return barberService.getAllBarbers();
  }

}
