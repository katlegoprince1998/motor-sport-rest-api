package com.codeworld.motorsportapp.service.user;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.entity.User;
import com.codeworld.motorsportapp.exceptions.UserAlreadyExistException;
import com.codeworld.motorsportapp.exceptions.UserNotFoundException;
import com.codeworld.motorsportapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImplementation implements UserService{
    private final UserRepository repository;
    @Override
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException {
        String userEmail = userDto.getEmail();
        User user = repository.findByEmail(userEmail);

        if (user == null){
            User user1 = User.builder()
                    .email(userEmail)
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .password(userDto.getPassword())
                    .build();

            BeanUtils.copyProperties(userDto, user1);
            repository.save(user1);
            userDto.setId(user1.getId());

        }else{
            throw new UserAlreadyExistException("User with this email already exist");

        }

        return userDto;
    }

    @Override
    public UserDto findUserById(Integer id) throws UserNotFoundException {
        Optional<User> optionalUser = repository.findById(id);
        UserDto userDto = new UserDto();

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            BeanUtils.copyProperties(user, userDto);
        }else{
            throw new UserNotFoundException("User with this id was not found");
        }

        return userDto;
    }
}
