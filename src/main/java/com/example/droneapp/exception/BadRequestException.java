package com.example.droneapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  private static final long serialVersionUID = 6171978443681353425L;

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public Map<String, String> handleValidationExceptions(
//          MethodArgumentNotValidException ex) {
//
//    System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult().getAllErrors().forEach((error) -> {
//      String fieldName = ((FieldError) error).getField();
//      String errorMessage = error.getDefaultMessage();
//      errors.put(fieldName, errorMessage);
//    });
//    return errors;
//  }


  public BadRequestException(String message) {
    super(message);
  }
}
