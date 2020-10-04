package com.ing.barber.shop.api.shop.model;

import com.ing.barber.shop.api.beans.Schedule;
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
@Document(value = "shops")
public class Shop {

  @Id
  @ApiModelProperty(notes="id is mandatory",required = true)
  private String id;
  private String name;
  private String address;
  private String avatar;
  private String phone;
  private String email;
  @Field(value = "schedules")
  private List<Schedule> schedules;

  @Override
  public String toString() {
    return "Shop{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", avatar='" + avatar + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", schedules=" + schedules +
        '}';
  }
}
