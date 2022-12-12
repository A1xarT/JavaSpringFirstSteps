package project3.RestApi.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {
    @Range(min = -100, max = 100, message = "Measurement values range is [-100,100]")
    @NotNull
    private Double value;
    @NotNull
    private Boolean isRaining;
    @NotNull
    private SensorDTO sensor;

    public MeasurementDTO(double value, boolean isRaining, SensorDTO sensorDTO) {
        this.value = value;
        this.isRaining = isRaining;
        this.sensor = sensorDTO;
    }

    public MeasurementDTO() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
