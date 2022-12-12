package project3.RestApi.utl.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project3.RestApi.dto.SensorDTO;
import project3.RestApi.services.SensorService;

@Component
public class SensorDtoValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDtoValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        if (sensorService.checkName(sensorDTO.getName())) {
            errors.rejectValue("name", "", "Sensor with such name already exists");
        }
    }
}
