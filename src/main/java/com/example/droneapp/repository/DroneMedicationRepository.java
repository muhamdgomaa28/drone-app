package com.example.droneapp.repository;

import com.example.droneapp.entities.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, Long> {
}
