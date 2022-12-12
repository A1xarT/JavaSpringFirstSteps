package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Measurement;
import models.Sensor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RestTemplateService {
    private final static RestTemplate restTemplate = new RestTemplate();
    private final static String resourceUrl = "http://localhost:8080/api";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RestTemplateService() {
    }

    public static List<Measurement> findAllMeasurementsBySensor(Sensor sensor) {
        String url = resourceUrl + "/measurements/search?name=" + sensor.getName();
        ResponseEntity<Measurement[]> response = restTemplate.getForEntity(url, Measurement[].class);
        var measurements = response.getBody();
        if (measurements == null) return List.of();
        return Arrays.stream(measurements).toList();
    }

    public static void insertRandomMeasurements(int number, Sensor sensor) throws IOException {
        String url = resourceUrl + "/measurements/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < number; i++) {
            var requestJson = objectMapper.writeValueAsString(Measurement.getRandomInstance(sensor));
            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }
        System.out.println("Success");
    }

    public static Sensor registerSensor(String name) {
        String url = resourceUrl + "/sensors/registration";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            var sensor = new Sensor(name);
            var requestJson = objectMapper.writeValueAsString(sensor);
            HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println("Success");
            return sensor;
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

    public static Sensor findSensorByName(String name) {
        try {
            String url = resourceUrl + "/sensors/search?name=" + name;
            ResponseEntity<Sensor> response = restTemplate.getForEntity(url, Sensor.class);
            System.out.println("Success");
            return response.getBody();
        } catch (Exception ex) {
            System.out.println("Error");
            return null;
        }
    }
}
