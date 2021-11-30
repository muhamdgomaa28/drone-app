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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("retrieve all drones");
        return droneRepository.findAll().stream().map(drone -> droneMapper.toModel(drone)).collect(Collectors.toList());
    }

    @Override
    public Drone registerDrone(DroneModel droneModel) {
        log.info("register new drone with serial number :{}", droneModel.getSerialNumber());

        Drone drone=  droneMapper.toEntity(droneModel);
        return droneRepository.save(drone);
    }

    @Override
    public void loadMedicationsToDrone(List<String> medicationCodes, String serialNumber) {
        log.info("load medication items to drone  with serial number :{}", serialNumber);

        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new BusinessException("Could not find Drone with serial Number provided"));
        List<Medication> medications = medicationRepository.findAllByCodeIn(medicationCodes);
        droneMedicationValidationService.validateMedicationsItemsWithDrone(medications, drone);
        assignMedicationsItemsToDrone(drone, medications);

    }

    @Override
    public Double checkDroneBatteryLevel(String serialNumber) {
        log.info("check battery level to drone  with serial number :{}",serialNumber);

        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new BusinessException("Could not find Drone with serial Number provided"));
        return drone.getBatteryCapacityPercentage();

    }

    @Override
    public List<DroneModel> getAvailableDroneForLoading() {
        log.info("get all drone ready for loading ");

        return droneRepository.findAllByStateIn(Arrays.asList(StateEnum.IDLE, StateEnum.LOADING))
                .stream()
                .map(drone -> droneMapper.toModel(drone))
                .collect(Collectors.toList());

    }

    @Override
    public void changeDroneState(String serialNumber, StateEnum stateEnum) {
        log.info("change drone status");
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new BusinessException("Could not find Drone with serial Number provided"));

        drone.setState(stateEnum);
        droneRepository.save(drone);

    }

    private void assignMedicationsItemsToDrone(Drone drone, List<Medication> medications) {
        medications.forEach(item -> {
            DroneMedication droneMedication = buildDroneMedication(drone, item);
            droneMedicationRepository.save(droneMedication);
        });
        changeDroneState(drone.getSerialNumber(), StateEnum.LOADING);
    }

    private DroneMedication buildDroneMedication(Drone drone, Medication item) {
        return DroneMedication
                .builder()
                .medication(item)
                .drone(drone)
                .assignedAt(LocalDateTime.now())
                .build();
    }


}
