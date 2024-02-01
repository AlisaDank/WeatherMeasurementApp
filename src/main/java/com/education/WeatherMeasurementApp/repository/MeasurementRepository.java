package com.education.WeatherMeasurementApp.repository;

import com.education.WeatherMeasurementApp.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
