package com.assignment.drone_delivery_service.controller;

import com.assignment.drone_delivery_service.model.dto.DroneRegistrationRequestDto;
import com.assignment.drone_delivery_service.model.dto.LoadDroneRequestDto;
import com.assignment.drone_delivery_service.model.entity.Drone;
import com.assignment.drone_delivery_service.model.entity.DroneMedicationMapping;
import com.assignment.drone_delivery_service.service.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/drones")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService service;

    /**
     * Registering a drone API
     * @param request - A JSON payload storing the details of a drone needed for registration
     * @return Drone entity - The drone record straight from database after successful creation
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Drone>> registerDrone(@Valid @RequestBody DroneRegistrationRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", service.processDroneRegistration(request)));
    }

    /**
     * Loading a drone with a medication API (Process delivery service)
     *   Additional feature: If the given medication code exists in the database, then don't push a new medication record in the db; Drone can only carry 1 load at a time.
     * @param request - A JSON payload storing the details like registered drone serial number and the medication to be loaded
     * @return A message detail stating the status of transaction/request whether failed or success.
     */
    @PostMapping("/load")
    public ResponseEntity<Map<String, String>> loadDrone(@Valid @RequestBody LoadDroneRequestDto request) {
        return ResponseEntity.ok(Map.of("message", service.processLoadingDrone(request)));
    }

    /**
     * Checking loaded medication for a given drone API
     * @param serialNumber - An identifier for searching a drone record to check if there is a loaded medication
     * @return DroneMedicationMapping entity - An active record from a join table between a drone and a medication. If none, returns an error message detail.
     */
    @GetMapping("/loaded/{serialNumber}")
    public ResponseEntity checkLoadedMedication(@PathVariable String serialNumber){
        DroneMedicationMapping deliveryInstance = service.checkLoadedMedication(serialNumber);
        if (deliveryInstance == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","There is no loaded medication on the drone with serial number: " + serialNumber));
        }
        return ResponseEntity.ok(Map.of("data",deliveryInstance));
    }

    /**
     * Check drone availability for loading API
     * @return List of Drone entities that is in IDLE state - meaning available for loading/delivery.
     */
    @GetMapping("/available")
    public ResponseEntity<Map<String, List<Drone>>> retrieveAvailableDrones(){
        return ResponseEntity.ok(Map.of("data", service.getAvailableDrones()));
    }

    /**
     * Check drone information API
     * @param serialNumber - An identifier for searching a drone record to check its information
     * @return Drone entity. If none, returns an error message detail.
     */
    @GetMapping("/{serialNumber}")
    public ResponseEntity checkDrone(@PathVariable String serialNumber){
        Drone drone = service.getDrone(serialNumber);
        if (drone == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","The drone with serial number: " + serialNumber + " does not exist."));
        }
        return ResponseEntity.ok(Map.of("data",drone));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleError(Exception ex) { // Handles general exceptions
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", ex.getCause().getMessage()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleDtoValidationError(MethodArgumentNotValidException ex) { // Handles field errors against validation constraints in DTOs
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", ex.getBindingResult().getFieldErrors().stream().findFirst().map(FieldError::getDefaultMessage).orElse("Validation error.")
        ));
    }
}
