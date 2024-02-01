package com.education.WeatherMeasurementApp.controller;

import com.education.WeatherMeasurementApp.dto.MeasurementDTO;
import com.education.WeatherMeasurementApp.dto.MeasurementWithSensorDTO;
import com.education.WeatherMeasurementApp.model.Measurement;
import com.education.WeatherMeasurementApp.service.MeasurementService;
import com.education.WeatherMeasurementApp.util.ErrorResponse;
import com.education.WeatherMeasurementApp.util.MeasurementValidator;
import com.education.WeatherMeasurementApp.util.NotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.education.WeatherMeasurementApp.util.ErrorsUtil.sendError;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final MeasurementValidator validator;

    @Autowired
    public MeasurementController(ModelMapper modelMapper, MeasurementService measurementService, MeasurementValidator validator) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.validator = validator;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.getAllMeasurements().stream().map(this::convertToMeasurementDTO).toList();
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementWithSensorDTO measurementWithSensorDTO,
                                                     BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementWithSensorDTO);
        validator.validate(measurement, bindingResult);
        sendError(bindingResult);

        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementWithSensorDTO measurementWithSensorDTO) {
        return modelMapper.map(measurementWithSensorDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
