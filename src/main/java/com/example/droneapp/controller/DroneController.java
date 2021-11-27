package com.example.droneapp.controller;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("drones")
public class DroneController {


    @Autowired
    private DroneService droneService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DroneModel> getAllDrones() {
        return droneService.getAllDrones();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Drone registerDrone(@Valid @RequestBody DroneModel droneModel) {
        return droneService.registerDrone(droneModel);
    }
}
