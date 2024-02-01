package com.education.WeatherMeasurementApp.util;

import com.education.WeatherMeasurementApp.model.Sensor;
import com.education.WeatherMeasurementApp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorService service;

    @Autowired
    public SensorValidator(SensorService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if (service.findByName(sensor.getName()) != null) {
            errors.rejectValue("name", "", "Sensor with this name is already created");
        }
    }
}
