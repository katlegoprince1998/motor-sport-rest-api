package com.codeworld.motorsportapp.service.user;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.exceptions.UserAlreadyExistException;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;

public interface UserService {
    UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
    UserDto findUserById(Integer id) throws UserNotFoundException;

}
