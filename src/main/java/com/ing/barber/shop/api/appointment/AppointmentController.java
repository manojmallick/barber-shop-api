package com.ing.barber.shop.api.appointment;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Appointment controller. */
@Slf4j
@RestController
@Api(value = "Appointment")
@RequestMapping("/v1/appointments")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AppointmentController {

  private final AppointmentService appointmentService;

  /**
   * Save appointment appointment response.
   *
   * @param appointment the appointment
   * @param httpRequest the http request
   * @return the appointment response
   */
  @PostMapping
  @ApiOperation(value = "save appointment", notes = "Returns the confirmed appointment details")
  public Appointment saveAppointment(
      @RequestBody @Valid final Appointment appointment, final HttpServletRequest httpRequest) {
    log.info("Made request to save Appointment API. [url={}]", httpRequest.getRequestURI());
    return appointmentService.saveAppointment(appointment);
  }

  @GetMapping
  public List<Appointment> getAllAppointments() {
    return appointmentService.getAllAppointments();
  }

  @GetMapping("/{id}")
  public Appointment getAppointment(@PathVariable(value = "id") final String id) {
    return appointmentService.getAppointment(id);
  }
}
