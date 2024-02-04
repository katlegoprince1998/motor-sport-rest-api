package com.codeworld.motorsportapp.service.auth;

import com.codeworld.motorsportapp.config.jwt.CustomeUserDetailsService;
import com.codeworld.motorsportapp.config.jwt.JwtProvider;
import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.entity.User;
import com.codeworld.motorsportapp.exceptions.UserAlreadyExistException;
import com.codeworld.motorsportapp.repository.UserRepository;
import com.codeworld.motorsportapp.request.Request;
import com.codeworld.motorsportapp.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final UserRepository userRepository;
    private final CustomeUserDetailsService customeUserDetailsService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerUser(UserDto userDto) throws Exception {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String firstname = userDto.getFirstname();
        String lastname = userDto.getLastname();

        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist != null){
            throw new UserAlreadyExistException("Email is already used by another account");
        }
        User createUser = User
                .builder()
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .password(passwordEncoder.encode(password))
                .build();
        BeanUtils.copyProperties(createUser, userDto);
        userRepository.save(createUser);
        userDto.setId(createUser.getId());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider
                .generateToken(authentication);

        AuthResponse authResponse = AuthResponse
                .builder()
                .jwt(token)
                .message("User Created Successfully")
                .build();
        return authResponse;
    }

    @Override

    public AuthResponse login(Request request) {
        String username = request.getEmail();
        String password = request.getPassword();


        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = AuthResponse
                .builder()
                .jwt(token)
                .message("User Logged In Successfully")
                .build();

        return authResponse;
    }


    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
        if(username == null){
            throw new BadCredentialsException("User Not Found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password is Incorrect");
        }
        return  new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());
    }

}
