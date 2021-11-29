package com.example.droneapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class DroneMedication {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    private LocalDateTime assignedAt;
}
