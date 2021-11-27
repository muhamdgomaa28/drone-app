package com.example.droneapp.entities;

import com.example.droneapp.model.enums.ModelEnum;
import com.example.droneapp.model.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Drone  implements Serializable {
    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private ModelEnum model;

    private double weightLimit;

    private double batteryCapacityPercentage;

    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @OneToMany(mappedBy = "drone")
    Set<DroneMedication> droneMedications;

}
