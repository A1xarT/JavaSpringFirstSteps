package project3.RestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project3.RestApi.models.Measurement;
import project3.RestApi.models.Sensor;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAllByIsRaining(boolean isRaining);

    long countByIsRaining(boolean isRaining);

    List<Measurement> findAllBySensor(Sensor sensor);
}
