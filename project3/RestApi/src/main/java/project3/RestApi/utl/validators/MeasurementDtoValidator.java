package project3.RestApi.utl.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project3.RestApi.dto.MeasurementDTO;
import project3.RestApi.services.SensorService;

@Component
public class MeasurementDtoValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementDtoValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (!sensorService.checkName(measurementDTO.getSensor().getName())) {
            errors.rejectValue("sensor", "", "Sensor with such name does not exist");
        }
    }
}
