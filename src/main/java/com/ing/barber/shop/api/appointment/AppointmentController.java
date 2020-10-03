package com.ing.barber.shop.api.appointment;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.service.AppointmentService;
import com.ing.barber.shop.api.customer.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AppointmentController {

    private AppointmentService appointmentService;
    @PostMapping
    public void createCustomer(@RequestBody Appointment appointment){
        appointmentService.createAppointment(appointment);
    }
}
