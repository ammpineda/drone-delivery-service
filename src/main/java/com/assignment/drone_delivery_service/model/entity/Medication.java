package com.assignment.drone_delivery_service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medication")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(
            regexp = "^[a-zA-Z0-9-_]+$",
            message = "Medication name must contain only letters, numbers, hyphen, or underscore."
    )
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(
            regexp = "^[A-Z0-9_]+$",
            message = "Medication code must contain only uppercase letters, numbers, or underscore."
    )
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "image")
    private String image;

}
