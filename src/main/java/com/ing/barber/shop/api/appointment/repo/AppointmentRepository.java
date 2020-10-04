package com.ing.barber.shop.api.appointment.repo;

import com.ing.barber.shop.api.appointment.model.Appointment;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

  @Query(value = "{'bookingDate':  { $gte: {$date:?0},$lte: {$date:?1}}}")
  List<Appointment> findAllAppointmentByScheduleDayOfWeek(Date bookingDate, Date endDate);


}
