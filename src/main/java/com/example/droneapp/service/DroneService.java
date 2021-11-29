package com.example.droneapp.service;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.DroneModel;

import java.util.List;

public interface DroneService {

    List<DroneModel> getAllDrones();
    Drone registerDrone(DroneModel drone);
    void loadMedicationsToDrone(List<String> medicationCodes, String serialNumber);
}
