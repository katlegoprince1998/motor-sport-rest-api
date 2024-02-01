package com.codeworld.motorsportapp.controller;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.dto.VehicleDto;

import com.codeworld.motorsportapp.service.user.UserService;
import com.codeworld.motorsportapp.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicle/api/v1/")
public class VehicleController {
    private final VehicleService service;
    private final UserService userService;

    @PostMapping("/create/user/{user_id}")
    public ResponseEntity<VehicleDto> createVehicle(
            @RequestBody VehicleDto vehicleDto,
            @PathVariable Integer user_id) throws Exception {
        UserDto user = userService.findUserById(user_id);

        return ResponseEntity.ok(service.createVehicle(vehicleDto, user));
    }
}
