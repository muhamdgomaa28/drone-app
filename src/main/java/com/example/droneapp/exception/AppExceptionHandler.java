package com.example.droneapp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
        String errorDescription = ex.getLocalizedMessage();
        if(errorDescription == null) errorDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage(new Date(),"Adam able to handle any exception: "+ errorDescription);
        return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
    }


    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request){
        StringBuilder errorDescription = new StringBuilder(ex.getClass()+": " + ex.getLocalizedMessage());
        ErrorMessage errorMessage = new ErrorMessage( new Date(),"Adam found no element eception: " + errorDescription);
        return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND );
    }
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request){
        String errorDescription = ex.getLocalizedMessage();
        if(errorDescription == null) errorDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage( new Date(),"Adam found error: " + errorDescription);
        return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
    }
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> userServiceException(BadRequestException ex, WebRequest request){
        String errorDescription = ex.getLocalizedMessage();
        if(errorDescription == null) errorDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage( new Date(),errorDescription);
        return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST );
    }
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> dataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request){
        String errorDescription = ex.getLocalizedMessage();
        if(errorDescription == null) errorDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage( new Date(),errorDescription);
        return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ApiError apiError = new ApiError(StatusCodeEnum.VALIDATION_ERROR);
        ErrorMessage errorMessage = new ErrorMessage();

        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String errorDefaultMessage = "";
        if (allErrors != null && allErrors.size() > 0) {
            for(ObjectError err : allErrors){
                errorDefaultMessage += err.getDefaultMessage() + " , ";
            }
        } else {
            FieldError fieldError = ex.getBindingResult().getFieldError();

            errorMessage.setMessage(String.format("%s %s %s %s", fieldError.getField(), "in", fieldError.getObjectName(), "not valid"));
            errorDefaultMessage = fieldError.getDefaultMessage();
        }
        errorMessage.setTimestamp(new Date());
        errorMessage.setMessage(errorDefaultMessage);


        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }



}