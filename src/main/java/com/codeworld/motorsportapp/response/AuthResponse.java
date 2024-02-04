package com.codeworld.motorsportapp.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String jwt;
    private String message;
}
