package com.assignment.drone_delivery_service.repository;

import com.assignment.drone_delivery_service.model.entity.Drone;
import com.assignment.drone_delivery_service.model.entity.DroneMedicationMapping;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DroneMedicationMappingRepository extends JpaRepository<DroneMedicationMapping, Long> {
    @Transactional
    public void deleteByDrone(Drone drone);

    public Optional<DroneMedicationMapping> findByDrone(Drone drone);

}
