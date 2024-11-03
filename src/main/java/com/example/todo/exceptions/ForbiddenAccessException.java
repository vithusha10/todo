package com.example.todo.exceptions;

public class ForbiddenAccessException extends RuntimeException  {
    public ForbiddenAccessException(String message) {
        super(message);
    }
}
