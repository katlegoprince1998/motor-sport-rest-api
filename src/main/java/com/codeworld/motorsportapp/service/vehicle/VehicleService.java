package com.codeworld.motorsportapp.service.vehicle;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.dto.VehicleDto;
import com.codeworld.motorsportapp.entity.User;
import com.codeworld.motorsportapp.entity.Vehicle;
import com.codeworld.motorsportapp.exceptions.VehicleNotFoundException;

import java.util.List;

public interface VehicleService {
     VehicleDto createVehicle(VehicleDto vehicleDto, UserDto user) throws Exception;
     VehicleDto findVehicleById(Integer id) throws VehicleNotFoundException;
     void deleteVehicle(Integer id) throws VehicleNotFoundException;

     VehicleDto updateVehicle(VehicleDto vehicleDto, Integer id) throws VehicleNotFoundException;

     List<VehicleDto> findAllVehicles();
     VehicleDto likedVehicles(Integer id, User user) throws VehicleNotFoundException;
}
