package com.example.droneapp;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.DroneMedication;
import com.example.droneapp.entities.Medication;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.model.MedicationModel;
import com.example.droneapp.model.enums.ModelEnum;
import com.example.droneapp.model.enums.StateEnum;
import com.example.droneapp.repository.DroneRepository;
import com.example.droneapp.repository.MedicationRepository;
import com.example.droneapp.service.MedicationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class MedicationServiceTest {

    @MockBean
    private MedicationRepository medicationRepository;

    @MockBean
    private DroneRepository droneRepository;

    @Autowired
    private MedicationService medicationService;




    @DisplayName("retrieve All Medications items per drone Should Return Valid Data")
    @Test
    void retrieveAllMedicationItemsShouldReturnValidDate() {

        Mockito.when(droneRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(buildDrone(1L,"123456",StateEnum.IDLE,ModelEnum.HEAVY_WEIGHT,500,50)));


        List<MedicationModel> medicationModels = medicationService.getLoadedMedicationItemsForDrone("123456");
        assertEquals(medicationModels.size(), 2);

        Assertions.assertThat(medicationModels)
                .hasSize(2)
                .extracting(MedicationModel::getCode)
                .containsExactly("MED1","MED2");

    }






    Drone buildDrone(long id, String serialNumber, StateEnum stateEnum, ModelEnum modelEnum, double weight, double batteryLevel){
        Drone drone = new Drone();
        drone.setId(id);
        drone.setState(stateEnum);
        drone.setSerialNumber(serialNumber);
        drone.setModel(modelEnum);
        drone.setBatteryCapacityPercentage(batteryLevel);
        drone.setWeightLimit(weight);

        List<DroneMedication> droneMedications = new ArrayList<>();

        DroneMedication droneMedication = new DroneMedication();
        droneMedication.setMedication(new Medication(1L, "medication1", 100,"MED1","HTTP://image.as",null));
        droneMedication.setDrone(drone);

        DroneMedication droneMedication2 = new DroneMedication();
        droneMedication2.setMedication(new Medication(2L, "medication2", 200,"MED2","HTTP://image.as",null));
        droneMedication2.setDrone(drone);

        droneMedications.addAll(Arrays.asList(droneMedication,droneMedication2));
        drone.setDroneMedications(droneMedications);
        return drone;
    }



}
