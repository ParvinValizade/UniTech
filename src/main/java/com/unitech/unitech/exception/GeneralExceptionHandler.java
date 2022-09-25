package com.unitech.unitech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistException(UserAlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordDoesNotValidException.class)
    public ResponseEntity<?> passwordDoesNotValidException(PasswordDoesNotValidException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PinDoesNotValidException.class)
    public ResponseEntity<?> pinDoesNotValidException(PinDoesNotValidException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> accountNotFoundException(AccountNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountIsDeactiveException.class)
    public ResponseEntity<?> accountIsDeactiveException(AccountIsDeactiveException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BalanceNotEnoughException.class)
    public ResponseEntity<?> balanceNotEnoughException(BalanceNotEnoughException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FromAndDestinationAccountsAreSameException.class)
    public ResponseEntity<?> fromAndDestinationAccountsAreSameException(
            FromAndDestinationAccountsAreSameException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SymbolNotFoundException.class)
    public ResponseEntity<?> symbolNotFoundExceptionHandler(SymbolNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
