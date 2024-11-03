package com.example.todo.services;

import com.example.todo.exceptions.UnauthorizedAccessException;
import com.example.todo.models.Todo;
import com.example.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo saveTodo(Todo todo, Long userId) {
        todo.getUser().setId(userId);
        return todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(Long id, Long userId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        if (!todo.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access to todo.");
        }
        return todo;
    }

    @Override
    public Page<Todo> getAllTodosByUser(Long userId, Pageable pageable) {
        return todoRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<Todo> searchTodosByUserAndKeyword(Long userId, String keyword) {
        return todoRepository.findByUserIdAndKeywords_KeywordContainingIgnoreCase(userId, keyword);
    }

    @Override
    public Todo markTodoAsCompleted(Long id, Long userId) {
        Todo todo = getTodoById(id, userId);
        todo.setIsCompleted(true);
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodoById(Long id, Long userId) {
        Todo todo = getTodoById(id, userId);
        todoRepository.delete(todo);
    }
}
