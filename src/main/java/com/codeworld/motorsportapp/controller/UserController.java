package com.codeworld.motorsportapp.controller;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.exceptions.UserAlreadyExistException;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/v1/")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(
            @RequestBody  UserDto userDto) throws UserAlreadyExistException {
        return ResponseEntity.ok(service.createUser(userDto));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(service.findUserById(id));
    }
}
