package com.example.todo.services;

import com.example.todo.models.User;
import com.example.todo.models.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
    User saveUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserByUsernameOrEmail(String username, String email);
    List<User> getAllUsers();
    void deleteUser(Long id);

    // Role management
    User addRoleToUser(Long userId, String roleName);
    User removeRoleFromUser(Long userId, String roleName);
}
