import models.Sensor;
import services.RestTemplateService;

import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        menu();
    }

    static void menu() throws IOException {
        System.out.println("Measurements table");
        Scanner myInput = new Scanner(System.in);
        Sensor currentSensor = null;
        int key;
        do {
            System.out.println("1 - random insert, 2 - show all, 3 - register new sensor, 4 - set sensor, 5 - show current sensor's name, 6 - clear, 0 - exit");
            key = myInput.nextInt();
            switch (key) {
                case 1 -> {
                    if (currentSensor == null) {
                        System.out.println("You should set sensor first");
                        break;
                    }
                    System.out.println("Input number of instances to insert:");
                    RestTemplateService.insertRandomMeasurements(myInput.nextInt(), currentSensor);
                }
                case 2 -> {
                    if (currentSensor == null) {
                        System.out.println("You should set sensor first");
                        break;
                    }
                    var measurements = RestTemplateService.findAllMeasurementsBySensor(currentSensor);
                    for (var m : measurements) {
                        System.out.println(m);
                    }
                }
                case 3 -> {
                    System.out.println("Input name of the sensor");
                    var sensor = RestTemplateService.registerSensor(myInput.next());
                    if (sensor != null) {
                        currentSensor = sensor;
                    }
                }
                case 4 -> {
                    System.out.println("Input name of the sensor");
                    currentSensor = RestTemplateService.findSensorByName(myInput.next());
                }
                case 5 -> {
                    if (currentSensor == null) {
                        System.out.println("Sensor is not set yet");
                    } else System.out.println("Current sensor' name: " + currentSensor.getName());
                }
                case 6 -> {
                    for (int clear = 0; clear < 1000; clear++) {
                        System.out.println("\b");
                    }
                }
                default -> {
                }
            }
        } while (key != 0);
    }
}
