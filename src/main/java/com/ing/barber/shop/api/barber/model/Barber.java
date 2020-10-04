package com.ing.barber.shop.api.barber.model;

import com.ing.barber.shop.api.beans.Schedule;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "barbers")
public class Barber {

  @Id
  private String id;
  private String name;
  private int experience;
  private String avatar;
  @Field(value = "schedules")
  private List<Schedule> schedules;
}
