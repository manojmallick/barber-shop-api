package com.ing.barber.shop.api.barber.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Barber {
    @Id
    public String id;
    public String name;
    public int experience;
    public String avatar;
}
