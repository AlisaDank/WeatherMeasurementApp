package com.education.WeatherMeasurementApp.service;

import com.education.WeatherMeasurementApp.model.Measurement;
import com.education.WeatherMeasurementApp.repository.MeasurementRepository;
import com.education.WeatherMeasurementApp.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public long getRainyDaysCount() {
        long count = 0L;
        List<Measurement> measurementsWithRaining = measurementRepository.findAll()
                .stream().filter(Measurement::isRaining).toList();
        if (!measurementsWithRaining.isEmpty()) count++;
        for (int i = 0; i < measurementsWithRaining.size() - 1; i++) {
            if (measurementsWithRaining.get(i).getCreatedDateTime().toLocalDate()
                    .equals(measurementsWithRaining.get(i + 1).getCreatedDateTime().toLocalDate())) {
                continue;
            }
            count += 1;
        }
        return count;
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        complement(measurement);
        measurementRepository.save(measurement);
    }


    private void complement(Measurement measurement) {
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedDateTime(LocalDateTime.now());
    }
}
