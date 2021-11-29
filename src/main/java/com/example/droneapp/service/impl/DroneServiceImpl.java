package com.example.droneapp.service.impl;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.DroneMedication;
import com.example.droneapp.entities.Medication;
import com.example.droneapp.exception.BusinessException;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.model.enums.StateEnum;
import com.example.droneapp.repository.DroneMedicationRepository;
import com.example.droneapp.repository.DroneRepository;
import com.example.droneapp.repository.MedicationRepository;
import com.example.droneapp.service.DroneMedicationValidationService;
import com.example.droneapp.service.DroneService;
import com.example.droneapp.service.mappers.DroneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private DroneMedicationValidationService droneMedicationValidationService;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private DroneMedicationRepository droneMedicationRepository;


    @Override
    public List<DroneModel> getAllDrones() {
        return droneRepository.findAll().stream().map(drone -> droneMapper.toModel(drone)).collect(Collectors.toList());
    }

    @Override
    public Drone registerDrone(DroneModel droneModel) {
        Drone drone=  droneMapper.toEntity(droneModel);
        return droneRepository.save(drone);
    }

    @Override
    public void loadMedicationsToDrone(List<String> medicationCodes, String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new BusinessException("Could not find Drone with serial Number provided"));
        List<Medication> medications = medicationRepository.findAllByCodeIn(medicationCodes);
        droneMedicationValidationService.validateMedicationWithDrone(medications, drone);
        assignMedicationsItemsToDrone(drone, medications);

    }

    private void assignMedicationsItemsToDrone(Drone drone, List<Medication> medications) {
        List<DroneMedication> droneMedications = new ArrayList<>();
        medications.forEach(item -> {
            DroneMedication droneMedication = new DroneMedication();
            droneMedication.setMedication(item);
            droneMedication.setDrone(drone);
            droneMedication.setAssignedAt(LocalDateTime.now());
            droneMedications.add(droneMedication);

        });
        droneMedicationRepository.saveAll(droneMedications);

        drone.setState(StateEnum.LOADING);
        droneRepository.save(drone);
    }
}
