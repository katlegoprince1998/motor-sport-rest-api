package com.codeworld.motorsportapp.controller.auth;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.request.Request;
import com.codeworld.motorsportapp.response.AuthResponse;
import com.codeworld.motorsportapp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth/")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(
            @RequestBody UserDto userDto
            ) {
        try {
            AuthResponse authResponse =
                    authenticationService.registerUser(userDto);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(
                    "Failed to create user",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Request request){
        try {
            AuthResponse loggedIn = authenticationService.login(request);
            return new ResponseEntity<>(loggedIn, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(
                    "Failed to log user in",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
