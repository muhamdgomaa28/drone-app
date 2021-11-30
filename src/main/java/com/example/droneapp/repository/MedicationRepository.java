package com.example.droneapp.repository;

import com.example.droneapp.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository  extends JpaRepository<Medication, Long> {

    List<Medication> findAllByCodeIn(List<String> medicationCodes);
}
