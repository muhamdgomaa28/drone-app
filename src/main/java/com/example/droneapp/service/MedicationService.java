package com.example.droneapp.service;


import com.example.droneapp.model.MedicationModel;

import java.util.List;

public interface MedicationService {

    List<MedicationModel> getAllMedications();
    List<MedicationModel> getLoadedMedicationItemsForDrone(String serialNumber);

}
