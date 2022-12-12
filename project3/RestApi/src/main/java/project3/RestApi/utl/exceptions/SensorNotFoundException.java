package project3.RestApi.utl.exceptions;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException() {
    }

    public SensorNotFoundException(String message) {
        super(message);
    }
}
