package com.ing.barber.shop.api.appointment;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/appointments")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping
    public void saveAppointment(@RequestBody @Valid Appointment appointment){
        appointmentService.saveAppointment(appointment);
    }
}
