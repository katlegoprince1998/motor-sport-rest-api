package com.codeworld.motorsportapp.service.user;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.exceptions.UserAlreadyExistException;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.request.Request;
import com.codeworld.motorsportapp.response.AuthResponse;

public interface UserService {
    UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
    UserDto findUserById(Integer id) throws UserNotFoundException;



}
