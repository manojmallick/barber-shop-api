package com.ing.barber.shop.api.appointment.repo;

import com.ing.barber.shop.api.appointment.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/** The interface Appointment repository. */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

  /**
   * Find all appointment by booking date and end date list.
   *
   * @param bookingDate the booking date
   * @param endDate the end date
   * @return the list
   */
  @Query(value = "{'bookingDate':  { $gte: {$date:?0},$lte: {$date:?1}}}")
  List<Appointment> findAllAppointmentByBookingDateAndEndDate(
      LocalDate bookingDate, LocalDate endDate);

  /**
   * Find all appointment by booking date and start time list.
   *
   * @param bookingDate the booking date
   * @param startTime the start time
   * @return the list
   */
  @Query(value = "{'bookingDate':  { $gte: {$date:?0},$lte: {$date:?0}} ,'startTime':?1}")
  List<Appointment> findAllAppointmentByBookingDateAndStartTime(
      LocalDate bookingDate, String startTime);
}
