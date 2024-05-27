package com.tawandr.utils.messaging.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    public GenericResponse(boolean success) {
        this.success = success;
    }

    protected boolean success = true;
    protected String message;
    protected int code;
}
