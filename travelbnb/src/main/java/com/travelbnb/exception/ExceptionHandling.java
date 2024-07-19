package com.travelbnb.exception;

import com.travelbnb.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> ResourceNotFound(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorDto error= new ErrorDto(
                ex.getMessage(),
                new Date(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> globalException(Exception ex, WebRequest webRequest){
        ErrorDto error= new ErrorDto(
                ex.getMessage(),
                new Date(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
