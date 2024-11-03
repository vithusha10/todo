package com.example.todo.services;

import com.example.todo.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoService {
    Todo saveTodo(Todo todo, Long userId);
    Todo getTodoById(Long id, Long userId);
    Page<Todo> getAllTodosByUser(Long userId, Pageable pageable); // Updated with pagination
    List<Todo> searchTodosByUserAndKeyword(Long userId, String keyword); // Search by keyword
    Todo markTodoAsCompleted(Long id, Long userId);
    void deleteTodoById(Long id, Long userId);
}
