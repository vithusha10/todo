package com.example.todo.payload.response;

import com.example.todo.models.Role;
import com.example.todo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Set<String> roles;
    private String token;
    private String event;
    public AuthResponse(User user, String token, String event) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
        this.token = token;
        this.event = event;
    }
}
