package com.example.droneapp.service;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.model.enums.StateEnum;

import java.util.List;

public interface DroneService {

    List<DroneModel> getAllDrones();
    Drone registerDrone(DroneModel drone);
    void loadMedicationsToDrone(List<String> medicationCodes, String serialNumber);
    Double checkDroneBatteryLevel(String serialNumber);
    List<DroneModel> getAvailableDroneForLoading();
    void changeDroneState(String serialNumber, StateEnum stateEnum);
}
