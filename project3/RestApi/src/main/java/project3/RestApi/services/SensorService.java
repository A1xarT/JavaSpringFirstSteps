package project3.RestApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.RestApi.models.Sensor;
import project3.RestApi.repositories.SensorRepository;
import project3.RestApi.utl.exceptions.SensorNotFoundException;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).orElseThrow(SensorNotFoundException::new);
    }

    public boolean checkName(String name) {
        return sensorRepository.findByName(name).isPresent();
    }

    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }
}
