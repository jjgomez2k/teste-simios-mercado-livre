package com.jjgomez2k.testesimios.exception;

import com.jjgomez2k.testesimios.model.ErrorMessage;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MongoWriteException.class})
    public ResponseEntity<Object> mongoWriteExceptionHandler(MongoWriteException ex, WebRequest request){

        ErrorMessage errrorMessage = ErrorMessage.builder()
                .timeStamp(LocalDateTime.now())
                .message("DNA already saved, try again!")
                .statusCode("400")
                .build();

        return new ResponseEntity<>(errrorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
