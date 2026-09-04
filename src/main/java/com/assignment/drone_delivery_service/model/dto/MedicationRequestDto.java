package com.assignment.drone_delivery_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MedicationRequestDto {

    @Pattern(
            regexp = "^[a-zA-Z0-9-_]+$",
            message = "Medication name must contain only letters, numbers, hyphen, or underscore."
    )
    private String name;

    @Pattern(
            regexp = "^[A-Z0-9_]+$",
            message = "Medication code must contain only uppercase letters, numbers, or underscore."
    )
    @NotBlank(message = "Medication code must be specified for drone delivery service.")
    private String code;

    private int weight;

    private String image;

}
