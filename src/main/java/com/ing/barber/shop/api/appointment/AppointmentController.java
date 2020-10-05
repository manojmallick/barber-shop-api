package com.ing.barber.shop.api.appointment;

import com.ing.barber.shop.api.appointment.model.Appointment;
import com.ing.barber.shop.api.appointment.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/appointments")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Api(value = "Appointment")
@Slf4j
public class AppointmentController {

  private AppointmentService appointmentService;

  @PostMapping(
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "save appointment",
      notes = "Returns the confirmed appointment details")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void saveAppointment(@RequestBody @Valid Appointment appointment,
      HttpServletRequest httpRequest) {
    log.info("Made request to get All Availability ByDate API. [url={}]",
        httpRequest.getRequestURI());
     appointmentService.saveAppointment(appointment);
  }


}
