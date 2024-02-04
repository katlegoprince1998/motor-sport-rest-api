package com.codeworld.motorsportapp.service.vehicle;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.dto.VehicleDto;
import com.codeworld.motorsportapp.entity.User;
import com.codeworld.motorsportapp.entity.Vehicle;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.exceptions.VehicleFieldsCannotBeEmptyException;
import com.codeworld.motorsportapp.exceptions.VehicleNotFoundException;
import com.codeworld.motorsportapp.repository.UserRepository;
import com.codeworld.motorsportapp.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository repository;
    private final UserRepository userRepository;

    @Override
    public VehicleDto createVehicle(
            VehicleDto vehicleDto, UserDto user)
            throws VehicleFieldsCannotBeEmptyException,
            UserNotFoundException {
        String getImage = vehicleDto.getImage();
        String getDescription = vehicleDto.getDescription();
        String getName = vehicleDto.getName();

        if (!getDescription.isEmpty() && !getName.isEmpty() && !getImage.isEmpty()) {
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent()) {
                Vehicle vehicle = Vehicle.builder()
                        .createdAt(LocalDate.now())
                        .price(vehicleDto.getPrice())
                        .image(getImage)
                        .likes(vehicleDto.getLikes())
                        .description(getDescription)
                        .name(getName)
                        .user(existingUser.get())
                        .build();

                repository.save(vehicle);
                vehicleDto.setId(vehicle.getId());
                vehicleDto.setCreatedAt(LocalDate.now());

                vehicleDto.setUser(existingUser.get());
                return vehicleDto;
            } else {

                throw new UserNotFoundException("User not found");
            }
        } else {

            throw new VehicleFieldsCannotBeEmptyException(
                    "Vehicle fields cannot be empty"
            );
        }
    }


    @Override
    public VehicleDto findVehicleById(Integer id) throws VehicleNotFoundException {
        Optional<Vehicle> optionalVehicle = repository.findById(id);

        if (optionalVehicle.isPresent()) {
            VehicleDto vehicleDto = new VehicleDto();
            BeanUtils.copyProperties(optionalVehicle.get(), vehicleDto);
            vehicleDto.setUser(optionalVehicle.get().getUser());
            return vehicleDto;
        } else {
            throw new VehicleNotFoundException("Vehicle with this id was not found");
        }
    }


    @Override
    public void deleteVehicle(Integer id) throws VehicleNotFoundException {
        Optional<Vehicle> optionalVehicle = repository.findById(id);
        if(optionalVehicle.isPresent()){
           repository.deleteById(id);
        }else {
            throw new VehicleNotFoundException("Vehicle with this id was not found");
        }

    }

    @Override
    public VehicleDto updateVehicle(VehicleDto vehicleDto, Integer id) throws VehicleNotFoundException {
        Vehicle oldVehicle = repository.findById(id).get();
        if(oldVehicle != null){
            if(Objects.nonNull(vehicleDto.getDescription()) &&
            !"".equalsIgnoreCase(vehicleDto.getDescription())){
                oldVehicle.setDescription(vehicleDto.getDescription());
            }
            if(Objects.nonNull(vehicleDto.getName()) &&
                    !"".equalsIgnoreCase(vehicleDto.getName())){
                oldVehicle.setName(vehicleDto.getName());
            }
            if(Objects.nonNull(vehicleDto.getImage()) &&
                    !"".equalsIgnoreCase(vehicleDto.getImage())){
                oldVehicle.setImage(vehicleDto.getImage());
            }
         if(oldVehicle.getPrice() != 0){
             oldVehicle.setPrice(vehicleDto.getPrice());
             repository.save(oldVehicle);
         }
         BeanUtils.copyProperties(oldVehicle, userRepository);

        }else {
            throw new VehicleNotFoundException("Vehicle with this id was not found");
        }
        return vehicleDto;
    }

    @Override
    public List<VehicleDto> findAllVehicles() {
        List<Vehicle> vehicles = repository.findAll();

        return vehicles.stream().map(
                vehicle -> new VehicleDto(
                        vehicle.getId(),
                        vehicle.getName(),
                        vehicle.getImage(),
                        vehicle.getDescription(),
                        vehicle.getPrice(),
                        vehicle.getCreatedAt(),
                        vehicle.getLikes(),
                        vehicle.getUser()
                )
        ).toList();
    }

    @Override
    public VehicleDto likedVehicles(Integer id, UserDto user) throws VehicleNotFoundException {
        Vehicle vehicle = repository.findById(id).get();
        VehicleDto vehicleDto = new VehicleDto();
        if(vehicle.getLikes().contains(user.getId())){
            vehicle.getLikes().remove(user.getId());
            repository.save(vehicle);
            BeanUtils.copyProperties(vehicle.getLikes(), vehicleDto.getLikes());
        }else{
            vehicle.getLikes().add(user.getId());
            repository.save(vehicle);
            BeanUtils.copyProperties(vehicle.getLikes(), vehicleDto.getLikes());
        }
        return vehicleDto;
    }

    @Override
    public Page<Vehicle> pagination(
            int offset, int pageSize, String field) {
        if("defaultValue".equals(field)){
           return repository.findAll(PageRequest.of(offset, pageSize));
        }else {
            return repository.findAll(
                    PageRequest.of(offset, pageSize)
                            .withSort(Sort.by(field)));
        }

    }
}
