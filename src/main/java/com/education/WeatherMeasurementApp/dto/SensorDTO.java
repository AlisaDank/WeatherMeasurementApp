package com.education.WeatherMeasurementApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Enter a sensor name")
    @Size(min = 3, max = 30, message = "Sensor name should have length between 3 and 30 symbols")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
