package com.unitech.unitech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AccountIsDeactiveException extends RuntimeException{
    public AccountIsDeactiveException(String message) {
        super(message);
    }
}
