package com.codeworld.motorsportapp.repository;

import com.codeworld.motorsportapp.dto.VehicleDto;
import com.codeworld.motorsportapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {

}
