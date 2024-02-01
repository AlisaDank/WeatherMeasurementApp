package com.education.WeatherMeasurementApp.controller;

import com.education.WeatherMeasurementApp.dto.SensorDTO;
import com.education.WeatherMeasurementApp.model.Sensor;
import com.education.WeatherMeasurementApp.service.SensorService;
import com.education.WeatherMeasurementApp.util.ErrorResponse;
import com.education.WeatherMeasurementApp.util.NotCreatedException;
import com.education.WeatherMeasurementApp.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.education.WeatherMeasurementApp.util.ErrorsUtil.sendError;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator validator;

    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator validator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        validator.validate(sensor, bindingResult);
        sendError(bindingResult);

        sensorService.addSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
