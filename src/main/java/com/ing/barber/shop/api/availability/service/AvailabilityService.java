package com.ing.barber.shop.api.availability.service;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.repo.AppointmentRepository;
import com.ing.barber.shop.api.availability.json.Availability;
import com.ing.barber.shop.api.beans.Day;
import com.ing.barber.shop.api.beans.Schedule;
import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AvailabilityService {

    private AppointmentRepository appointmentRepository;
    private ShopRepository shopRepository;

    public List<Availability> getAllAvailabilityByDate(Date date) {
        //get the today's day
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("Current day = " + Day.values()[dayOfWeek - 1]);

        Shop shop = shopRepository.findAll().get(0);

        System.out.println("shop = " + shop.getSchedules().stream());

        List<Schedule> schedules = shop.getSchedules()
                .stream()
                .filter(s -> s.getDayOfWeek().equalsIgnoreCase(Day.values()[dayOfWeek - 1].toString()))
                .collect(Collectors.toList());

        System.out.println("Today schedule = " + schedules.get(0));

        List<Appointment> appointments=appointmentRepository.findAllAppointmentByScheduleDayOfWeek(date);

        return null;

    }

}
