package com.ing.barber.shop.api.appointment.model;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.beans.Customer;
import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.shop.model.Shop;
import java.util.Date;
import java.util.List;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "appointments")
@CompoundIndexes({
    @CompoundIndex(name = "barber_index", def = "{'startTime' : 1, 'bookingDate': 1,  'barber':1}", unique = true),
    @CompoundIndex(name = "customer_index", def = "{'startTime' : 1, 'bookingDate': 1, 'customer.email':1}", unique = true)
})
public class Appointment {

  @Id
  private String id;

  private Customer customer;

  @DBRef
  private Barber barber;

  @DBRef
  private List<Service> services;

  @DBRef
  private Shop shop;

  @NotEmpty(message = "start time can't be empty")
  private String startTime;

  @NotEmpty(message = "end time can't be empty")
  private String endTime;

  @NotNull(message = "booking time can't be empty")
  @FutureOrPresent(message = "booking date can't be from past")
  private Date bookingDate;
}
