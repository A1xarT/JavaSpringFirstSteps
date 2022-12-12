package project3.RestApi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project3.RestApi.dto.SensorDTO;
import project3.RestApi.models.Sensor;
import project3.RestApi.services.SensorService;
import project3.RestApi.utl.SensorErrorResponse;
import project3.RestApi.utl.common.SensorCommonUtil;
import project3.RestApi.utl.exceptions.SensorNotCreatedException;
import project3.RestApi.utl.exceptions.SensorNotFoundException;
import project3.RestApi.utl.validators.SensorDtoValidator;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static project3.RestApi.utl.common.SensorCommonUtil.convertToSensorDTO;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorDtoValidator sensorDtoValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorDtoValidator sensorDtoValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorDtoValidator = sensorDtoValidator;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(SensorCommonUtil::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public SensorDTO getSensor(@RequestParam("name") String name) {
        return convertToSensorDTO(sensorService.findByName(name));
    }

    private Sensor enrichOnCreation(Sensor sensor) {
        sensor.setCreatedAtNow();
        return sensor;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        sensorDtoValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (var field : bindingResult.getFieldErrors()) {
                stringBuilder.append(field.getField()).append(" - ").append(field.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(stringBuilder.toString());
        }
        sensorService.save(enrichOnCreation(convertToSensor(sensorDTO)));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findById(id));
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor was not found;" + e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor was not created;" + e.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
