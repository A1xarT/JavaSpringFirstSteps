package project3.RestApi.utl.exceptions;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String message) {
        super(message);
    }

    public MeasurementNotCreatedException() {
    }
}
