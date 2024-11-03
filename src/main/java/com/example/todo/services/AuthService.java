package com.example.todo.services;

import com.example.todo.payload.request.LoginRequest;
import com.example.todo.payload.request.RegisterRequest;
import com.example.todo.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse authenticateUser(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
