import models.Measurement;
import models.Sensor;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import services.RestTemplateService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) throws IOException {
        menu();
    }

    static void menu() throws IOException {
        System.out.println("Measurements table");
        Scanner myInput = new Scanner(System.in);
        Sensor currentSensor = null;
        List<Measurement> measurements = null;
        int key;
        do {
            System.out.println("1 - random insert, 2 - show all, 3 - register new sensor, 4 - set sensor, 5 - show current sensor's name, 6 - clear, 7 - flowchart, 0 - exit");
            key = myInput.nextInt();
            switch (key) {
                case 1 -> {
                    if (currentSensor == null) {
                        System.out.println("You should set sensor first");
                        break;
                    }
                    System.out.println("Input number of instances to insert:");
                    RestTemplateService.insertRandomMeasurements(myInput.nextInt(), currentSensor);
                    measurements = getMeasurements(currentSensor);
                }
                case 2 -> {
                    if (currentSensor == null) {
                        System.out.println("You should set sensor first");
                        break;
                    }
                    measurements = RestTemplateService.findAllMeasurementsBySensor(currentSensor);
                    for (var m : measurements) {
                        System.out.println(m);
                    }
                }
                case 3 -> {
                    System.out.println("Input name of the sensor");
                    var sensor = RestTemplateService.registerSensor(myInput.next());
                    if (sensor != null) {
                        currentSensor = sensor;
                        measurements = getMeasurements(sensor);
                    }
                }
                case 4 -> {
                    System.out.println("Input name of the sensor");
                    var sensor = RestTemplateService.findSensorByName(myInput.next());
                    currentSensor = sensor;
                    measurements = getMeasurements(sensor);
                }
                case 5 -> {
                    if (currentSensor == null) {
                        System.out.println("You should set sensor first");
                    } else System.out.println("Current sensor' name: " + currentSensor.getName());
                }
                case 6 -> {
                    for (int clear = 0; clear < 1000; clear++) {
                        System.out.println("\b");
                    }
                }
                case 7 -> {
                    if (measurements == null) {
                        System.out.println("You should set sensor first");
                    } else {
                        List<Double> xValues = new ArrayList<>() {
                        }, yValues = new ArrayList<>() {
                        };
                        for (int i = 0; i < measurements.size(); i++) {
                            xValues.add((double) i);
                            yValues.add(measurements.get(i).getValue());
                        }
                        flowChart(Stream.of(xValues.toArray(Double[]::new)).mapToDouble(Double::doubleValue).toArray(),
                                Stream.of(yValues.toArray(Double[]::new)).mapToDouble(Double::doubleValue).toArray());
                    }
                }
                default -> {
                }
            }
        } while (key != 0);
    }

    public static void flowChart(double[] xData, double[] yData) {
        if (xData == null || xData.length == 0 || yData == null || yData.length == 0)
            return;
        // Create Chart
        XYChart chart = QuickChart.getChart("Measurements", "Measurement#", "Value", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }

    private static List<Measurement> getMeasurements(Sensor sensor) {
        return RestTemplateService.findAllMeasurementsBySensor(sensor);
    }
}
