package com.example.droneapp.service.impl;

import com.example.droneapp.entities.Drone;
import com.example.droneapp.entities.Medication;
import com.example.droneapp.exception.BusinessException;
import com.example.droneapp.model.enums.StateEnum;
import com.example.droneapp.service.DroneMedicationValidationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DroneMedicationValidationServiceImpl implements DroneMedicationValidationService {


    @Override
    public void validateMedicationsItemsWithDrone(List<Medication> medications, Drone drone) {
        validateDroneStatus(drone);
        validateDroneBatteryLevel(drone);
        validateMedicationWeightAgainstDrone(medications, drone);
    }


    public void validateDroneStatus(Drone drone) {
    if(!drone.getState().equals(StateEnum.IDLE) && !drone.getState().equals(StateEnum.LOADING)){
        throw new BusinessException("Drone not ready to loaded items with state "+ drone.getState());
    }
    }

    public void validateDroneBatteryLevel(Drone drone) {
        if(drone.getBatteryCapacityPercentage() <= 25) {
            throw new BusinessException("Drone not ready to loaded items with battery " + drone.getBatteryCapacityPercentage());
        }
    }

    public void validateMedicationWeightAgainstDrone(List<Medication> medications, Drone drone){

        Double allMedicationsWeight = 0.0;
        Double availableSpace= 0.0;
         if(drone.getState().equals(StateEnum.LOADING)) {
             List<Medication> loadedItemsOnDrone = getLoadedItemsWithinDrone(drone);
             allMedicationsWeight = getMedicationsWeight(loadedItemsOnDrone) + getMedicationsWeight(medications);
             availableSpace = drone.getWeightLimit() - getMedicationsWeight(loadedItemsOnDrone);
         }
         else {
             allMedicationsWeight =  getMedicationsWeight(medications);

         }

        if(allMedicationsWeight > drone.getWeightLimit()) {
            throw new BusinessException("Drone not ready to loaded items with weight " + allMedicationsWeight +  " space available in drone "+ (drone.getState().equals(StateEnum.LOADING) ? availableSpace: drone.getWeightLimit()));

        }
    }

    private List<Medication> getLoadedItemsWithinDrone(Drone drone) {
        return drone.getDroneMedications()
                .stream()
                .map(droneMedication -> droneMedication.getMedication())
                .collect(Collectors.toList());
    }



    private Double getMedicationsWeight(List<Medication> medications) {
        Double medicationsWeight = medications
                .stream()
                .map(medication -> medication.getWeight())
                .reduce(0.0 , Double::sum);
        return medicationsWeight;
    }


}
