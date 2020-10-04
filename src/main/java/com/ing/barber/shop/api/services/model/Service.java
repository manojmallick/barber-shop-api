package com.ing.barber.shop.api.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "services")
public class Service {
    @Id
    private String id;
    private String name;
    private double amount;
    private ServiceType type;
}
