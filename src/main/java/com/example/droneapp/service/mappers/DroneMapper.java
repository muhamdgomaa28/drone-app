package com.example.droneapp.service.mappers;


import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.DroneModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    Drone toEntity(DroneModel droneModel);
    DroneModel toModel(Drone drone);
}