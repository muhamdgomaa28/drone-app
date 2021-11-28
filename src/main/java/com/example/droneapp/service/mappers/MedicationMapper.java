package com.example.droneapp.service.mappers;

import com.example.droneapp.entities.Medication;
import com.example.droneapp.model.MedicationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    Medication toEntity(MedicationModel medicationModel);
    Medication toModel(Medication medication);
}

