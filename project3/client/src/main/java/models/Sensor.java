package models;

public class Sensor {
    private String name;

    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
