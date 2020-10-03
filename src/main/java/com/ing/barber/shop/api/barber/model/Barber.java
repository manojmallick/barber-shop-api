package com.ing.barber.shop.api.barber.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Barber {
    @Id
    public String id;
    public String name;
    public int experience;
    public String avatar;

    public Barber() {
    }

    public Barber(String id, String name, int experience, String avatar) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
