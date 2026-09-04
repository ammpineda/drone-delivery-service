package com.assignment.drone_delivery_service.repository;

import com.assignment.drone_delivery_service.model.entity.Drone;
import com.assignment.drone_delivery_service.model.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByCode(String code);

}
