package com.ing.barber.shop.api.appointment.repo;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.shop.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    @Query(value = "{'bookingDate': ?0}")
    List<Appointment> findAllAppointmentByScheduleDayOfWeek(Date bookingDate);
}
