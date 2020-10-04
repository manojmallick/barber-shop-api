package com.ing.barber.shop.api.barber.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.barber.shop.api.beans.Schedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description="Customer")
public class Barber {

  @Id
  @ApiModelProperty(notes="id is mandatory",required = true)
  private String id;
  private String name;
  private int experience;
  private String avatar;
  @JsonIgnore
  @Field(value = "schedules")
  private List<Schedule> schedules;
}
