package com.example.todo.services;

import com.example.todo.models.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role getRoleById(Long id);
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
    void deleteRole(Long id);
}
