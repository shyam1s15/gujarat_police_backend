package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<?> dataNotFound(DataNotFoundException dataNotFoundException){
        return new ResponseEntity<>(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
