package com.ing.barber.shop.api.services.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Services {
    @Id
    public String id;
    public String name;
    public double amount;
    public ServiceType avatar;

}
