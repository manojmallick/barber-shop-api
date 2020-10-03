package com.ing.barber.shop.api.appointment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Appointment {
    @Id
    public String id;
    public String name;
    public String gender;
    public String email;
    public String mobile;
}
