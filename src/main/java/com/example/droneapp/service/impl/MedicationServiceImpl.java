package com.example.droneapp.service.impl;

import com.example.droneapp.entities.Medication;
import com.example.droneapp.model.MedicationModel;
import com.example.droneapp.repository.MedicationRepository;
import com.example.droneapp.service.MedicationService;
import com.example.droneapp.service.mappers.MedicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationMapper medicationMapper;

    @Override
    public List<MedicationModel> getAllMedications() {
        return medicationRepository.findAll().stream().map(medication -> medicationMapper.toModel(medication)).collect(Collectors.toList());

    }
}
