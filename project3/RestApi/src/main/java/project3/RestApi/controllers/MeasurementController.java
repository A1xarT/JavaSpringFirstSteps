package project3.RestApi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project3.RestApi.dto.MeasurementDTO;
import project3.RestApi.models.Measurement;
import project3.RestApi.services.MeasurementService;
import project3.RestApi.services.SensorService;
import project3.RestApi.utl.MeasurementErrorResponse;
import project3.RestApi.utl.SensorErrorResponse;
import project3.RestApi.utl.exceptions.MeasurementNotCreatedException;
import project3.RestApi.utl.exceptions.MeasurementNotFoundException;
import project3.RestApi.utl.exceptions.SensorNotFoundException;
import project3.RestApi.utl.validators.MeasurementDtoValidator;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static project3.RestApi.utl.common.SensorCommonUtil.convertToSensorDTO;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final MeasurementDtoValidator measurementDtoValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper, MeasurementDtoValidator measurementDtoValidator) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.measurementDtoValidator = measurementDtoValidator;
    }

    @GetMapping("/search")
    public List<MeasurementDTO> getMeasurements(@RequestParam("name") String name) {
        return measurementService.findAllBySensor(sensorService.findByName(name)).stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public HttpEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        measurementDtoValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (var field : bindingResult.getFieldErrors()) {
                stringBuilder.append(field.getField()).append(" - ").append(field.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(stringBuilder.toString());
        }
        measurementService.save(enrichOnCreation(convertToMeasurement(measurementDTO)));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(@RequestParam("raining") Optional<Boolean> isRaining) {
        if (isRaining.isPresent())
            return measurementService.findByRaining(isRaining.get()).stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount() {
        return measurementService.countByIsRaining(true);
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementService.findById(id));
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        var measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
        measurementDTO.setSensor(convertToSensorDTO(measurement.getSensor()));
        return measurementDTO;
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        var measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setSensor(sensorService.findByName(measurementDTO.getSensor().getName()));
        return measurement;
    }

    private Measurement enrichOnCreation(Measurement measurement) {
        measurement.setCreatedAtNow();
        return measurement;
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Measurement was not found;" + e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Measurement was not created;" + e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor was not found;" + e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
