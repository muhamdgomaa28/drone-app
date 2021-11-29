package com.example.droneapp.controller;

import com.example.droneapp.model.DroneModel;
import com.example.droneapp.model.MedicationModel;
import com.example.droneapp.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicationController {

    @Autowired
    private MedicationService medicationService;


    @GetMapping("/medications")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicationModel> getAllMedications() {
        return medicationService.getAllMedications();
    }


    @GetMapping("/medications/drone")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicationModel> getLoadedMedicationItemsByDrone(@RequestParam String serialNumber) {
        return medicationService.getLoadedMedicationItemsForDrone(serialNumber);
    }


}
