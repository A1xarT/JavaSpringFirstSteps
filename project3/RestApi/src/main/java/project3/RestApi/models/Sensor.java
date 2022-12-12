package project3.RestApi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @Size(message = "Sensor's name must be in range from 3 to 30")
    private String name;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
        this.setCreatedAtNow();
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAtNow() {
        this.createdAt = LocalDateTime.now();
    }
}
