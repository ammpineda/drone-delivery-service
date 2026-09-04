package com.assignment.drone_delivery_service.service;

import com.assignment.drone_delivery_service.model.constants.DroneState;
import com.assignment.drone_delivery_service.model.entity.Drone;
import com.assignment.drone_delivery_service.repository.DroneMedicationMappingRepository;
import com.assignment.drone_delivery_service.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.assignment.drone_delivery_service.model.constants.DroneState.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DroneStateScheduler {

    private final DroneRepository droneRepository;
    private final DroneMedicationMappingRepository droneMedicationMappingRepository;

    // Runs every 10 secs
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void processStateTransitions() {
        List<Drone> allDrones = droneRepository.findAll();

        for(Drone drone : allDrones) {
            String currentState = drone.getState();
            switch (currentState){
                case "LOADING":
                    drone.setState(LOADED.getDisplayValue());
                    break;
                case "LOADED":
                    drone.setState(DELIVERING.getDisplayValue());
                    break;
                case "DELIVERING":
                    drone.setState(DELIVERED.getDisplayValue());
                    break;
                case "DELIVERED":
                    drone.setState(RETURNING.getDisplayValue());
                    break;
                case "RETURNING":
                    drone.setState(IDLE.getDisplayValue());
                    drone.setBatteryCapacity(Math.max(0, drone.getBatteryCapacity() - 25)); // Functional requirement: Reduce battery percentage for each delivery after completion
                    droneMedicationMappingRepository.deleteByDrone(drone); // Remove delivery instance from drone_medication_mapping table
                    break;
                default:
                    continue;
            }
        }
    }


}