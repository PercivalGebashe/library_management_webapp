package com.github.percivalgebashe.assignment_5.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFound(ResourceNotFoundException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Resource Not Found");
    }

    @ExceptionHandler(value = NoContentException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ErrorMessage noContent(NoContentException ex) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "No Content Found");
    }
}
