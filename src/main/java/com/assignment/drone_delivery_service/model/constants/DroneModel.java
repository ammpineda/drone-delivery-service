package com.assignment.drone_delivery_service.model.constants;

import lombok.Getter;

@Getter
public enum DroneModel {
    // Maximum capacity of a drone is defined based on chosen drone model:
    LIGHTWEIGHT("Lightweight", 200),
    MIDDLEWEIGHT("Middleweight", 500),
    CRUISERWEIGHT("Cruiserweight", 750),
    HEAVYWEIGHT("Heavyweight", 1000);

    private final String displayValue;
    private final int maxCapacity;

    DroneModel(String displayValue, int maxCapacity) {
        this.displayValue = displayValue;
        this.maxCapacity = maxCapacity;
    }
}
