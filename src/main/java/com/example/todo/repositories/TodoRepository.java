package com.example.todo.repositories;

import com.example.todo.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserId(Long userId);
    // Pagination and sorting support
    Page<Todo> findByUserId(Long userId, Pageable pageable);
    // Search by keyword
    List<Todo> findByUserIdAndKeywords_KeywordContainingIgnoreCase(Long userId, String keyword);
}
