package com.assignment.drone_delivery_service.model.dto;

import com.assignment.drone_delivery_service.model.constants.DroneModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

/**
 *  Request DTO (Data Transfer Object) for 'Register Drone' endpoint ("/api/drones/add" POST)
 */
@Getter
public class DroneRegistrationRequestDto implements Serializable {

    @NotBlank(message = "Serial number is required for drone registration.")
    @Size(min = 1, max = 100, message = "The length of serial number must be between 1 and 100")
    private String serialNumber;

    @NotNull(message = "Model type (0 - LIGHTWEIGHT, 1 - MIDDLEWEIGHT, 2 - CRUISERWEIGHT, 3 - HEAVYWEIGHT) is required for drone registration.")
    private DroneModel model;

}
