package com.unitech.unitech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FromAndDestinationAccountsAreSameException extends RuntimeException{

    public FromAndDestinationAccountsAreSameException(String message) {
        super(message);
    }
}
