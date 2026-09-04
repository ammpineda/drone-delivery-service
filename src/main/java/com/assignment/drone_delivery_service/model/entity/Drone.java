package com.assignment.drone_delivery_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.assignment.drone_delivery_service.model.constants.DroneState.*;

@Entity
@Table(name = "drone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "serial_number", unique = true, length = 100, nullable = false)
    private String serialNumber;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "weight_limit", nullable = false)
    private int weightLimit; // The max capacity a drone can carry in grams

    @Column(name = "battery_capacity")
    private int batteryCapacity; // The current battery capacity of a drone in percentage (default at 100)

    @Column(name = "state")
    private String state; // The current state of a drone (default is "LOADING")


}
