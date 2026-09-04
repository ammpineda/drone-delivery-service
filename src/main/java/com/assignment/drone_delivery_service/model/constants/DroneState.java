package com.assignment.drone_delivery_service.model.constants;

import lombok.Getter;

@Getter
public enum DroneState {

    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String displayValue;

    DroneState(String displayValue){
        this.displayValue = displayValue;
    }

}
