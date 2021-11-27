package com.example.droneapp.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double weight;
    private String code;
    private String imageUrl;

    @OneToMany(mappedBy = "medication")
    Set<DroneMedication> droneMedications;
}
