package com.ing.barber.shop.api.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.beans.Customer;
import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.shop.model.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Appointment used for both request and response of Appointment booking api
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "appointments")
@ApiModel(description = "Appointment")
@CompoundIndexes({
    @CompoundIndex(name = "barber_index", def = "{'startTime' : 1, 'bookingDate': 1,  'barber':1}", unique = true),
    @CompoundIndex(name = "customer_index", def = "{'startTime' : 1, 'bookingDate': 1, 'customer.email':1}", unique = true)
})
public class Appointment {

  @Id
  private String id;

  @NotNull(message = "customer details can't be empty")
  @Valid
  @ApiModelProperty(notes = "customer details is mandatory.", required = true)
  private Customer customer;

  @DBRef
  @ApiModelProperty(notes = "barber details is mandatory only while selecting barber.", required = false)
  @Valid
  private Barber barber;

  @DBRef
  private List<Service> services;

  @DBRef
  @NotNull(message = "shop details can't be empty")
  @Valid
  @ApiModelProperty(notes = "shop details is mandatory.", required = true)
  private Shop shop;

  @NotEmpty(message = "start time can't be empty")
  @ApiModelProperty(notes = "start time is mandatory.", required = true)
  private String startTime;

  @JsonIgnore
  private String endTime;

  @NotNull(message = "booking time can't be empty")
  @Valid
  @FutureOrPresent
  @ApiModelProperty(notes = "booking date only be present or future dates.", required = true)
  private LocalDate bookingDate;
}
