package com.gova.EasyGuide.exceptions;


import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {


    @ExceptionHandler(AllExceptions.userAllReadyExist.class)
    public ResponseEntity<String> userallreadyPresent(AllExceptions.userAllReadyExist ex)
    {
        return  new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AllExceptions.courseAllReadyExist.class)
    public ResponseEntity<String> courseExistWithName(AllExceptions.courseAllReadyExist ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AllExceptions.userNotFound.class)
    public ResponseEntity<String> UserNotFoundWithId(AllExceptions.userNotFound ex)
    {
        return  new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AllExceptions.invalidCredentails.class)
    public ResponseEntity<String> inavalidCredentails(AllExceptions.invalidCredentails ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<String> resourceAllreadyExist(AllExceptions.resourceAllreadyExist ex)
    {
        return  new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

}
