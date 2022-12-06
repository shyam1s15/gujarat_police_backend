package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.exceptions.DataAlreadyExistException;
import com.shyam.gujarat_police.exceptions.DataInsertionException;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.exceptions.ExcelException;
import com.shyam.gujarat_police.response.APIResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExceptionController {

    @ExceptionHandler(value = DataNotFoundException.class)
    public APIResponse dataNotFound(DataNotFoundException dataNotFoundException){
//        return new ResponseEntity<>(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return APIResponse.error(dataNotFoundException.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public APIResponse handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
        return APIResponse.error("File too large!");
    }


    @ExceptionHandler(value = ExcelException.class)
    public APIResponse excelProblem(ExcelException excelException){
//        return new ResponseEntity<>(excelException.getMessage(), HttpStatus.EXPECTATION_FAILED);
        return APIResponse.error(excelException.getMessage());
    }


    @ExceptionHandler(value = DataAlreadyExistException.class)
    public APIResponse dataAlreadyExists(DataAlreadyExistException dataNotFoundException){
//        return new ResponseEntity<>(dataNotFoundException.getMessage(), HttpStatus.EXPECTATION_FAILED);
        return APIResponse.error(dataNotFoundException.getMessage());
    }

    @ExceptionHandler(value = DataInsertionException.class)
    public APIResponse dataInsertionException(DataInsertionException dataInsertionException){
//        return new ResponseEntity<>(dataInsertionException.getMessage(), HttpStatus.EXPECTATION_FAILED);
        return APIResponse.error(dataInsertionException.getMessage());
    }
}
