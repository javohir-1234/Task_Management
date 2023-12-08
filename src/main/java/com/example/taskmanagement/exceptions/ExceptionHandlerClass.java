package com.example.taskmanagement.exceptions;

import com.example.taskmanagement.exceptions.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(value = UserNotVerified.class)
    public ResponseEntity<ExceptionMessage> notVerifiedUser(UserNotVerified e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage("User not verified", 404));
    }

    @ExceptionHandler(value = DataNotFound.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> dataNotFound(DataNotFound e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(e.getMessage(), 404));
    }

    @ExceptionHandler(value = NotCorrectEmail.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> notCorrectEmail(NotCorrectEmail e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(e.getMessage(), 404));
    }
}
