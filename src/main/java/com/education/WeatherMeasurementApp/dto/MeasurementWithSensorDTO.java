package com.education.WeatherMeasurementApp.dto;

import com.education.WeatherMeasurementApp.model.Sensor;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementWithSensorDTO {
    @NotNull(message = "This field should not be empty")
    @Range(min = -100, max = 100, message = "Measurement's value should be between '-100' and '100")
    private Float value;
    @NotNull(message = "This field should not be empty")
    private Boolean raining;
    @NotNull(message = "You should add a sensor")
    private Sensor sensor;

    public Float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
