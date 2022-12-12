package project3.RestApi.services;

import org.springframework.stereotype.Service;
import project3.RestApi.models.Measurement;
import project3.RestApi.models.Sensor;
import project3.RestApi.repositories.MeasurementRepository;
import project3.RestApi.utl.exceptions.MeasurementNotFoundException;

import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public List<Measurement> findAllBySensor(Sensor sensor) {
        return measurementRepository.findAllBySensor(sensor);
    }

    public Measurement findById(int id) {
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    public long countByIsRaining(boolean isRaining) {
        return measurementRepository.countByIsRaining(isRaining);
    }

    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public List<Measurement> findByRaining(boolean isRaining) {
        return measurementRepository.findAllByIsRaining(isRaining);
    }
}
