package com.ing.barber.shop.api.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Services {
    @Id
    public String id;
    public String name;
    public double amount;
    public ServiceType avatar;
}
