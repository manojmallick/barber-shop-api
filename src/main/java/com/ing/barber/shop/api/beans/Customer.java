package com.ing.barber.shop.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @NotEmpty(message = "name can't be empty.")
    public String name;
    @NotEmpty(message = "gender can't be empty.")
    public String gender;
    @Email
    @Size(max=100,message = "Email id must be less than 100 characters.")
    public String email;
    @NotEmpty(message = "mobile number can't be empty.")
    public String mobile;
}
