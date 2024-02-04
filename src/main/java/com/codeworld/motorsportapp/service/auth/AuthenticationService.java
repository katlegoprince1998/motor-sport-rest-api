package com.codeworld.motorsportapp.service.auth;

import com.codeworld.motorsportapp.dto.UserDto;
import com.codeworld.motorsportapp.request.Request;
import com.codeworld.motorsportapp.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse registerUser(UserDto userDto) throws Exception;
    AuthResponse login(Request request);
}
