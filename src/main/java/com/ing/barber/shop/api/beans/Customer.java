package com.ing.barber.shop.api.beans;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Customer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {


  @ApiModelProperty(notes = "name is mandatory", required = true)
  @NotEmpty(message = "name can't be empty.")
  private String name;

  @ApiModelProperty(notes = "gender is mandatory", required = true)
  @NotEmpty(message = "gender can't be empty.")
  private String gender;

  @Email
  @Size(max = 100, message = "Email must be less than 100 characters.")
  @ApiModelProperty(notes = "Email is mandatory & to be valid and less than 100 characters", required = true)
  private String email;

  @NotEmpty(message = "mobile number can't be empty.")
  @ApiModelProperty(notes = "Mobile number mandatory", required = true)
  private String mobile;
}
