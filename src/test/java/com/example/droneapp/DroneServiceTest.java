package com.example.droneapp;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.DroneMedication;
import com.example.droneapp.entities.Medication;
import com.example.droneapp.exception.BusinessException;
import com.example.droneapp.model.DroneModel;
import com.example.droneapp.model.enums.ModelEnum;
import com.example.droneapp.model.enums.StateEnum;
import com.example.droneapp.repository.DroneMedicationRepository;
import com.example.droneapp.repository.DroneRepository;
import com.example.droneapp.repository.MedicationRepository;
import com.example.droneapp.service.DroneService;
import com.example.droneapp.service.mappers.DroneMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DroneServiceTest {

    @MockBean
    private DroneRepository droneRepository;
    @Autowired
    private DroneService droneService;
    @Autowired
    private DroneMapper droneMapper;

    @MockBean
    private MedicationRepository medicationRepository;

    @MockBean
    DroneMedicationRepository droneMedicationRepository;

    Drone drone;
    DroneMedication droneMedication;

    @BeforeEach
    void prepareMocksOutputs() {
        when(droneRepository.findAll()).thenReturn(buildDroneModels());
        when(droneRepository.findAllByStateIn(any())).thenReturn(getAvailableDroneModels());

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(buildDrone(1L,"123456",StateEnum.IDLE,ModelEnum.HEAVY_WEIGHT,500,50)));


        when(droneRepository.save(any(Drone.class))).then(invocationOnMock -> {
           drone = invocationOnMock.getArgument(0);
           return this.drone;
        });

        when(medicationRepository.findAllByCodeIn(any())).thenReturn(buildMedicationModels());

    }



    @DisplayName("retrieve All Drones Should Return Valid Data")
    @Test
    void retrieveAllDroneShouldReturnValidDate() {
            List<DroneModel> returnedDrones =droneService.getAllDrones();
            assertEquals(returnedDrones.size(), 4);

            Assertions.assertThat(returnedDrones)
                    .hasSize(4)
                    .extracting(DroneModel::getId)
                    .containsExactly(1L,2L,3L,4L);

    }

    @DisplayName("get Available Drone For Loading Should Return Valid Data")
    @Test
    void getAvailableDroneForLoadingShouldReturnValidDate() {
        List<DroneModel> returnedDrones =droneService.getAvailableDroneForLoading();
        assertEquals(returnedDrones.size(), 3);

        Assertions.assertThat(returnedDrones)
                .hasSize(3)
                .extracting(DroneModel::getState)
                .containsExactly(StateEnum.IDLE,StateEnum.IDLE,StateEnum.LOADING);

    }

    @DisplayName("check Drone Battery Level Should Return Valid Data")
    @Test
    void checkDroneBatteryLevelShouldReturnValidDate() {
        Double batteryLevel = droneService.checkDroneBatteryLevel("123456");
        Drone drone = buildDrone(1L,"123456",StateEnum.IDLE,ModelEnum.HEAVY_WEIGHT,500,50);
        assertEquals(batteryLevel, drone.getBatteryCapacityPercentage());

    }

    @DisplayName("register Drone  Should add it")
    @Test
    void registerDroneShouldAddIt() {
        droneService.registerDrone(droneMapper.toModel(buildDrone(1L,"123456",StateEnum.IDLE,ModelEnum.HEAVY_WEIGHT,500,50)));

        verify(droneRepository).save(drone);
    }

    @DisplayName("load Medications ToDrone with valid data Should assign it")
    @Test
    void loadMedicationsToDroneWithValidDataShouldAddIt() {
        List<String> medicationsItems =Arrays.asList("MED1","MED2","MED3");
        droneService.loadMedicationsToDrone(medicationsItems , "123456");

        verify(droneMedicationRepository, times(3)).save(any());
        verify(droneRepository).save(drone);

    }


    @DisplayName("load Medications ToDrone with invalid weight exceed drone limit Should throw exception")
    @Test
    void loadMedicationsToDroneWithInValidWeightShouldThrowException() {
        List<String> medicationsItems =Arrays.asList("MED1","MED2","MED3");

        Medication medication = new Medication(1L, "medication1", 100,"MED1","HTTP://image.as",null);
        Medication medication2 = new Medication(2L, "medication2", 400,"MED2","HTTP://image.as",null);
        Medication medication3 = new Medication(3L, "medication3", 50,"MED3","HTTP://image.as",null);

        when(medicationRepository.findAllByCodeIn(any())).thenReturn(Arrays.asList(medication, medication2, medication3));

        Throwable exception = assertThrows(
                BusinessException.class,
                () -> {
                    droneService.loadMedicationsToDrone(medicationsItems , "123456");
                }
        );
        assertEquals("Drone not ready to loaded items with weight 550.0 space available in drone 500.0", exception.getMessage());

    }

    @DisplayName("load Medications ToDrone with drone invalid state ( not equal idle or loading)  Should throw exception")
    @Test
    void loadMedicationsToDroneWithInValidStateShouldThrowException() {
        List<String> medicationsItems =Arrays.asList("MED1","MED2","MED3");

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(buildDrone(1L,"123456",StateEnum.LOADED,ModelEnum.HEAVY_WEIGHT,500,50)));


        Throwable exception = assertThrows(
                BusinessException.class,
                () -> {
                    droneService.loadMedicationsToDrone(medicationsItems , "123456");
                }
        );
        assertEquals("Drone not ready to loaded items with state LOADED", exception.getMessage());

    }


    @DisplayName("load Medications ToDrone with drone with battery level Should throw exception")
    @Test
    void loadMedicationsToDroneWithInValidBatteryLevelShouldThrowException() {
        List<String> medicationsItems =Arrays.asList("MED1","MED2","MED3");

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(Optional.of(buildDrone(1L,"123456",StateEnum.IDLE,ModelEnum.HEAVY_WEIGHT,500,20)));


        Throwable exception = assertThrows(
                BusinessException.class,
                () -> {
                    droneService.loadMedicationsToDrone(medicationsItems , "123456");
                }
        );
        assertEquals("Drone not ready to loaded items with battery 20.0", exception.getMessage());

    }



    Drone buildDrone(long id,String serialNumber, StateEnum stateEnum,ModelEnum modelEnum, double weight, double batteryLevel){
        Drone drone = new Drone();
        drone.setId(id);
        drone.setState(stateEnum);
        drone.setSerialNumber(serialNumber);
        drone.setModel(modelEnum);
        drone.setBatteryCapacityPercentage(batteryLevel);
        drone.setWeightLimit(weight);
        return drone;
    }





    List<Drone> buildDroneModels(){
        Drone drone = new Drone(1L, "123456", ModelEnum.HEAVY_WEIGHT, 200,100,StateEnum.IDLE,null);
        Drone drone2 = new Drone(2L, "123233", ModelEnum.HEAVY_WEIGHT, 4000,100,StateEnum.IDLE,null);
        Drone drone3 = new Drone(3L, "123443", ModelEnum.HEAVY_WEIGHT, 300,70,StateEnum.LOADING,null);
        Drone drone4 = new Drone(4L, "123555", ModelEnum.HEAVY_WEIGHT, 500,50,StateEnum.LOADED,null);
        return  Arrays.asList(drone, drone2, drone3, drone4);
    }

    List<Medication> buildMedicationModels(){
        Medication medication = new Medication(1L, "medication1", 100,"MED1","HTTP://image.as",null);
        Medication medication2 = new Medication(2L, "medication2", 200,"MED2","HTTP://image.as",null);
        Medication medication3 = new Medication(3L, "medication3", 50,"MED3","HTTP://image.as",null);

        return  Arrays.asList(medication, medication2, medication3);
    }

    List<Drone> getAvailableDroneModels(){
        Drone drone = new Drone(1L, "123456", ModelEnum.HEAVY_WEIGHT, 200,100,StateEnum.IDLE,null);
        Drone drone2 = new Drone(2L, "123233", ModelEnum.HEAVY_WEIGHT, 4000,100,StateEnum.IDLE,null);
        Drone drone3 = new Drone(3L, "123443", ModelEnum.HEAVY_WEIGHT, 300,70,StateEnum.LOADING,null);
        return  Arrays.asList(drone, drone2, drone3);
    }


}
