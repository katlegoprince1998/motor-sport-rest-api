package com.codeworld.motorsportapp.controller;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.dto.VehicleDto;

import com.codeworld.motorsportapp.entity.Vehicle;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.exceptions.VehicleDtoError;
import com.codeworld.motorsportapp.exceptions.VehicleNotFoundException;
import com.codeworld.motorsportapp.service.user.UserService;
import com.codeworld.motorsportapp.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vehicle/v1/")
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
    @GetMapping("/get/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(
            @PathVariable Integer id) throws VehicleNotFoundException {
        return ResponseEntity.ok(service.findVehicleById(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>>
    delete(@PathVariable Integer id)
            throws VehicleNotFoundException {
        Map<String, String> response = new HashMap<>();

        try{
            service.deleteVehicle(id);
            response.put("message", "Vehicle deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
          response.put("message", "Error deleting the vehicle");
          return new ResponseEntity<>(response,
                  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<Object> getAllVehicles(){
       try{
           List<VehicleDto> vehicleDto = service.findAllVehicles();
           return new ResponseEntity<>(vehicleDto, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(
                   "Failed to get vehicles",
                   HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>>
    updateVehicle(@RequestBody VehicleDto vehicleDto,
                  @PathVariable Integer id) throws VehicleNotFoundException {
        Map<String, String> response = new HashMap<>();
        try{
                    service.updateVehicle(vehicleDto, id);
                    response.put("message", "Vehicle update successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("message", "Failed to update vehicle");
            return new ResponseEntity<>
                    (response,
                            HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Vehicle>> paginaton(
            @PathVariable int offset,
            @PathVariable int pageSize,
            @RequestParam(required = false, defaultValue = "defaultValue") String field
    ){
        Page<Vehicle> vehiclePage =
                service.pagination(offset, pageSize, field);

        if (vehiclePage.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(vehiclePage);
        }
    }

    @PutMapping("/like/vehicle/{id}/user/{userId}")
    public ResponseEntity<Map<String,String>> likeUnlikeVehicle(
            @PathVariable Integer id,
            @PathVariable Integer userId
    ) throws UserNotFoundException, VehicleNotFoundException {
        UserDto user = userService.findUserById(userId);
        Map<String, String> response = new HashMap<>();
        try{
            response.put("message", "Vehicle liked/unliked");
            service.likedVehicles(id, user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("message", "Failed to like/unlike vehicle");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
