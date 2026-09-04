package com.assignment.drone_delivery_service.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 *  Request DTO (Data Transfer Object) for 'Load Drone with Medication' endpoint ("/api/drones/load`" POST)
 */
@Getter
public class LoadDroneRequestDto {

    @NotBlank(message = "Serial number of a registered drone is required for drone delivery service.")
    private String serialNumber;

    @NotNull(message = "Medication load must be provided for drone delivery service.")
    @Valid
    private MedicationRequestDto medication;
}
