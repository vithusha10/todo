package com.example.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle generic RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.warn("Bad request : {} - at URI: {}", ex.getMessage(), request.getDescription(true));
        return "Bad Request: " + ex.getMessage();
    }

    // Handle UnauthorizedAccessException with a 401 Unauthorized status
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccess(UnauthorizedAccessException ex, WebRequest request) {
        logger.warn("Unauthorized access: {} - at URI: {}", ex.getMessage(), request.getDescription(true));
        return "Forbidden: " + ex.getMessage();
    }

    // Handle UnauthorizedAccessException with a 403 Forbidden status
    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenAccess(ForbiddenAccessException ex, WebRequest request) {
        logger.warn("Forbidden access: {} - at URI: {}", ex.getMessage(), request.getDescription(true));
        return "Forbidden: " + ex.getMessage();
    }

    // Handle 404 errors when no handler is found
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NoHandlerFoundException ex, WebRequest request) {
        logger.warn("Resource Not Found 404 : {} - at URI: {}", ex.getMessage(), request.getDescription(true));
        return "Resource not found 404: " + ex.getRequestURL();
    }

    // Handle validation errors (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Validation Error : {} - at URI: {}", ex.getMessage(), request.getDescription(true));
        return "Validation error: " + ex.getBindingResult().getFieldError().getDefaultMessage();
    }

    // Handle 500 Internal Server Error for unexpected exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, WebRequest request) {
        logger.error("Exception occurred: {} - at URI: {}", ex.getMessage(), request.getDescription(false), ex);
        return "Internal server error: Please contact support.";
    }
}
