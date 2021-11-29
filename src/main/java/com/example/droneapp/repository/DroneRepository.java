package com.example.droneapp.repository;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findBySerialNumber(String serialNumber);

    List<Drone> findAllByStateIn(List<StateEnum> stateEnumList);
}
