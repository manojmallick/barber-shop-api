package com.ing.barber.shop.api.appointment.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Appointment response. will be used for sending the api response saveAppointment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

  private String appointmentId;

}
