package com.assignment.drone_delivery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroneDeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneDeliveryServiceApplication.class, args);
	}

}
