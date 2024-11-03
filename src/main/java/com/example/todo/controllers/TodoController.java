package com.example.todo.controllers;

import com.example.todo.models.Todo;
import com.example.todo.models.User;
import com.example.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    private Long getUserId(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo, Authentication authentication) {
        Long userId = getUserId(authentication);
        return todoService.saveTodo(todo, userId);
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserId(authentication);
        return todoService.getTodoById(id, userId);
    }

    @GetMapping
    public Page<Todo> getAllTodos(Pageable pageable, Authentication authentication) {
        Long userId = getUserId(authentication);
        return todoService.getAllTodosByUser(userId, pageable);
    }

    @GetMapping("/search")
    public List<Todo> searchTodos(@RequestParam String keyword, Authentication authentication) {
        Long userId = getUserId(authentication);
        return todoService.searchTodosByUserAndKeyword(userId, keyword);
    }

    @PutMapping("/{id}/complete")
    public Todo markTodoAsCompleted(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserId(authentication);
        return todoService.markTodoAsCompleted(id, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserId(authentication);
        todoService.deleteTodoById(id, userId);
    }
}
