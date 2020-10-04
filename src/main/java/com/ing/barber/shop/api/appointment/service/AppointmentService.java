package com.ing.barber.shop.api.appointment.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AppointmentService {
    private AppointmentRepository appointmentRepository;

    @Transactional
    public void saveAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }
}
