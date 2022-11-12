package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.exceptions.ExcelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExceptionController {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<?> dataNotFound(DataNotFoundException dataNotFoundException){
        return new ResponseEntity<>(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }

    @ExceptionHandler(value = ExcelException.class)
    public ResponseEntity<?> excelProblem(ExcelException excelException){
        return new ResponseEntity<>(excelException.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
}
