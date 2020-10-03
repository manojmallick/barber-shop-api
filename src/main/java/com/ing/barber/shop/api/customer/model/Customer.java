package com.ing.barber.shop.api.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    public String id;
    public String name;
    public String gender;
    public String email;
    public String mobile;
}
