package com.ing.barber.shop.api.availability.json;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Availability.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Availability {

  private String id;
  private Set<String> timeSlot;
}
