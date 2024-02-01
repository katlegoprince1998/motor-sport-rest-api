package com.codeworld.motorsportapp.repository;

import com.codeworld.motorsportapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
}
