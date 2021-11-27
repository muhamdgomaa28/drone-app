package com.example.droneapp.service.impl;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.repository.DroneRepository;
import com.example.droneapp.service.DroneService;
import com.example.droneapp.service.impl.mappers.DroneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {
    
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneMapper droneMapper;


    @Override
    public List<DroneModel> getAllDrones() {
        return droneRepository.findAll().stream().map(drone -> droneMapper.toModel(drone)).collect(Collectors.toList());
    }

    @Override
    public Drone registerDrone(DroneModel droneModel) {
        Drone drone=  droneMapper.toEntity(droneModel);
        return droneRepository.save(drone);
    }
}
