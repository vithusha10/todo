package com.example.todo.services;

import com.example.todo.exceptions.UnauthorizedAccessException;
import com.example.todo.models.Keyword;
import com.example.todo.models.Todo;
import com.example.todo.models.User;
import com.example.todo.repositories.KeywordRepository;
import com.example.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final KeywordRepository keywordRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, KeywordRepository keywordRepository) {
        this.todoRepository = todoRepository;
        this.keywordRepository = keywordRepository;
    }

    @Override
    public Todo saveTodo(Todo todo,  User user) {
        todo.setUser(user);
        todo = todoRepository.save(todo);
        Set<Keyword> keywords = new HashSet<>();
        todo.getKeywords().forEach(keyword -> {
            keywords.add(keywordRepository.findById(keyword.getId()).orElse(null));
        });
        todo.setKeywords(keywords);
        return todo;
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
