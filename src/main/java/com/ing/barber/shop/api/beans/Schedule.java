package com.ing.barber.shop.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "Schedule{" +
                "dayOfWeek=" + dayOfWeek +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
