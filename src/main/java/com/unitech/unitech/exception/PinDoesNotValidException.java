package com.unitech.unitech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PinDoesNotValidException extends RuntimeException{

    public PinDoesNotValidException(String message) {
        super(message);
    }
}
