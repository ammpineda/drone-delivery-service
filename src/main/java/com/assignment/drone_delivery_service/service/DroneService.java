package com.assignment.drone_delivery_service.service;

import com.assignment.drone_delivery_service.model.dto.DroneRegistrationRequestDto;
import com.assignment.drone_delivery_service.model.dto.LoadDroneRequestDto;
import com.assignment.drone_delivery_service.model.dto.MedicationRequestDto;
import com.assignment.drone_delivery_service.model.entity.Drone;
import com.assignment.drone_delivery_service.model.entity.DroneMedicationMapping;
import com.assignment.drone_delivery_service.model.entity.Medication;
import com.assignment.drone_delivery_service.repository.DroneMedicationMappingRepository;
import com.assignment.drone_delivery_service.repository.DroneRepository;
import com.assignment.drone_delivery_service.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.assignment.drone_delivery_service.model.constants.DroneState.IDLE;
import static com.assignment.drone_delivery_service.model.constants.DroneState.LOADING;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMedicationMappingRepository droneMedicationMappingRepository;

    public Drone processDroneRegistration(DroneRegistrationRequestDto request) {
        Drone drone = Drone.builder()
                .serialNumber(request.getSerialNumber())
                .model(request.getModel().getDisplayValue())
                .weightLimit(request.getModel().getMaxCapacity())
                .batteryCapacity(100) // Battery capacity at full 100% by default
                .state(IDLE.getDisplayValue()) // Drone state by default
                .build();

        return droneRepository.save(drone);
    }

    public String processLoadingDrone(LoadDroneRequestDto request) {
        // 1. Validates registered drone using given serial number from the payload.
        Drone registeredDrone = droneRepository.findBySerialNumber(request.getSerialNumber()).orElse(null);
        if(registeredDrone == null) {
            return "The drone with serial number: " + request.getSerialNumber() + " could not be processed for delivery because it does not exist.";
        }

        // 2. Retrieves an existing medication based on given code. If not, create a new medication record from the payload.
        Medication medication = retrieveMedication(request.getMedication());
        if(medication == null){
            return "The drone with serial number: " + request.getSerialNumber() + " could not be processed for delivery because of missing medication code from the request.";
        }

        // 3. Functional requirement: Prevent drone been overloaded than maximum capacity.
        if(medication.getWeight() > registeredDrone.getWeightLimit()) {
            return "The drone with serial number: " + request.getSerialNumber() + " could not be processed for delivery because the weight of load exceeded the drone's max capacity.";
        }

        // 4. Functional requirement: Prevent drone enter to LOADING state is the battery level is below 25%.
        if(registeredDrone.getBatteryCapacity() < 25) {
            return "The drone with serial number: " + request.getSerialNumber() + " could not be processed for delivery because the current battery capacity of drone is less than 25%.";
        }

        // 5. Prevent drone from getting used if drone is not in IDLE state
        if(!IDLE.getDisplayValue().equalsIgnoreCase(registeredDrone.getState())) {
            return "The drone with serial number: " + request.getSerialNumber() + " could not be processed for delivery because it is currently being used.";
        }

        // 6. Process loading drone with medication.
        else {
            // Updates drone's state to LOADING state
            registeredDrone.setState(LOADING.getDisplayValue());
            droneRepository.save(registeredDrone);

            // Creates delivery instance for chosen drone and loaded medication
            DroneMedicationMapping delivery = DroneMedicationMapping.builder()
                    .drone(registeredDrone)
                    .medication(medication)
                    .build();
            droneMedicationMappingRepository.save(delivery);
            return "The drone with serial number: " + request.getSerialNumber() + " is now in transit for delivery!";
        }

    }

    private Medication retrieveMedication(MedicationRequestDto request) {
        // Checks if there is an existing medication record using the given medication code from the request
        if (!request.getCode().isBlank()) {
            Medication existingMedication = medicationRepository.findByCode(request.getCode()).orElse(null);
            if (existingMedication == null) { // if not existing, creates a new record
                Medication newMedication = Medication.builder()
                        .name(request.getName())
                        .code(request.getCode())
                        .weight(request.getWeight())
                        .image(request.getImage())
                        .build();
                return medicationRepository.save(newMedication);
            } else // if existing, exits method
                return existingMedication;
        }
        return null;
    }

    public DroneMedicationMapping checkLoadedMedication(String serialNumber){
        Drone drone = droneRepository.findBySerialNumber(serialNumber).orElse(null);
        if(drone == null){
            return null;
        }
        return droneMedicationMappingRepository.findByDrone(drone).orElse(null);
    }

    public List<Drone> getAvailableDrones(){
        List<Drone> availableDrones = droneRepository.findAll();
        return availableDrones.stream().filter(d -> IDLE.getDisplayValue().equalsIgnoreCase(d.getState())).collect(Collectors.toList());
    }

    public Drone getDrone(String serialNumber){
        return droneRepository.findBySerialNumber(serialNumber).orElse(null);
    }

}
