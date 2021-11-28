package com.example.droneapp.repository;

import com.example.droneapp.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository  extends JpaRepository<Medication, Long> {


}
