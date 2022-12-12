package project3.RestApi.utl.exceptions;

public class MeasurementNotFoundException extends RuntimeException {
    public MeasurementNotFoundException(String message) {
        super(message);
    }

    public MeasurementNotFoundException() {
    }
}
