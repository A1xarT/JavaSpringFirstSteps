package models;

import java.util.concurrent.ThreadLocalRandom;

public class Measurement {
    private double value;
    private boolean raining;
    private Sensor sensor;

    public Measurement(double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public Measurement() {
    }

    public static Measurement getRandomInstance(Sensor sensor) {
        Measurement measurement = new Measurement();
        measurement.setRaining(ThreadLocalRandom.current().nextBoolean());
        measurement.setValue(ThreadLocalRandom.current().nextDouble(-100, 100));
        measurement.setSensor(sensor);
        return measurement;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
