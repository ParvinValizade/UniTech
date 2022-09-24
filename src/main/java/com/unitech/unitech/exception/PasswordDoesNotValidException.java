package com.unitech.unitech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordDoesNotValidException extends RuntimeException{

    public PasswordDoesNotValidException(String message) {
        super(message);
    }
}
