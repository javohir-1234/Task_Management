package com.example.taskmanagement.exceptions;

import com.example.taskmanagement.exceptions.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(value = UserNotVerified.class)
    public ResponseEntity<ExceptionMessage> notVerifiedUser(UserNotVerified e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage("User not verified", 404));
    }
}
