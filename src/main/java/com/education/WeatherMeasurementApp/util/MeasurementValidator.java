package com.education.WeatherMeasurementApp.util;

import com.education.WeatherMeasurementApp.model.Measurement;
import com.education.WeatherMeasurementApp.model.Sensor;
import com.education.WeatherMeasurementApp.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (sensorService.findByName(measurement.getSensor().getName()) == null) {
            errors.rejectValue("sensor", "", "This sensor not found");
        }

    }
}
