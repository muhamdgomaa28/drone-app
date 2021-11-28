package com.example.droneapp.service.impl;

import com.example.droneapp.model.MedicationModel;
import com.example.droneapp.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationService medicationService;

    @Override
    public List<MedicationModel> getAllMedications() {
        return null;
    }
}
