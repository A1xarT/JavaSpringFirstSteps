package project3.RestApi.utl.exceptions;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String message) {
        super(message);
    }

    public SensorNotCreatedException() {
    }
}
