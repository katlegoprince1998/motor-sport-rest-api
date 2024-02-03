package com.codeworld.motorsportapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@Data

public class VehicleDtoError {
    private String message;

    public VehicleDtoError(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
