package com.example.droneapp.service;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.Medication;

import java.util.List;

public interface DroneMedicationValidationService {

    void validateMedicationsItemsWithDrone(List<Medication> medications, Drone drone);
}
