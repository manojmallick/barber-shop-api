package com.ing.barber.shop.api.barber;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.service.BarberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/barbers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberController {

    private BarberService barberService;
    @PostMapping
    public void createCustomer(@RequestBody Barber barber){
        barberService.createBarber(barber);
    }
}
