package project3.RestApi.utl.common;

import org.modelmapper.ModelMapper;
import project3.RestApi.dto.SensorDTO;
import project3.RestApi.models.Sensor;

public class SensorCommonUtil {
    private final static ModelMapper modelMapper = new ModelMapper();
    public static SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
