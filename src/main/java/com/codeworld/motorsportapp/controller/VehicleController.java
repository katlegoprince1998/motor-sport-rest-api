package com.codeworld.motorsportapp.controller;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.dto.VehicleDto;

import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.exceptions.VehicleDtoError;
import com.codeworld.motorsportapp.service.user.UserService;
import com.codeworld.motorsportapp.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicle/api/v1/")
public class VehicleController {
    private final VehicleService service;
    private final UserService userService;

    @PostMapping("/create/user/{user_id}")

    public ResponseEntity<Object> createVehicle(
            @RequestBody VehicleDto vehicleDto,
            @PathVariable Integer user_id) {
        try {
            UserDto user = userService.findUserById(user_id);
            VehicleDto createdVehicle = service.createVehicle(vehicleDto, user);
            return ResponseEntity.ok(createdVehicle);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VehicleDtoError(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new VehicleDtoError("Internal server error"));
        }
    }

}
