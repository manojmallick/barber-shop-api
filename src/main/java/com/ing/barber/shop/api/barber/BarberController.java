package com.ing.barber.shop.api.barber;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.service.BarberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
