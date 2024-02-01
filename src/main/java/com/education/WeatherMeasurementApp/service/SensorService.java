package com.education.WeatherMeasurementApp.service;

import com.education.WeatherMeasurementApp.model.Sensor;
import com.education.WeatherMeasurementApp.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void addSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
