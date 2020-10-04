package com.ing.barber.shop.api.availability;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.availability.json.Availability;
import com.ing.barber.shop.api.availability.service.AvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/v1/availabilities")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @GetMapping
    public List<Availability> getAllAvailabilityByDate(){
        return availabilityService.getAllAvailabilityByDate(new Date());
    }
}