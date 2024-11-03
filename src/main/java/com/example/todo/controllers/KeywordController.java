package com.example.todo.controllers;

import com.example.todo.models.Keyword;
import com.example.todo.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Keyword> createKeyword(@RequestBody Keyword keyword) {
        Keyword savedKeyword = keywordService.saveKeyword(keyword);
        return new ResponseEntity<>(savedKeyword, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Keyword> getKeywordById(@PathVariable Long id) {
        Keyword keyword = keywordService.getKeywordById(id);
        return new ResponseEntity<>(keyword, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Keyword> getKeywordByText(@RequestParam String keywordText) {
        Keyword keyword = keywordService.getKeywordByText(keywordText);
        if (keyword != null) {
            return new ResponseEntity<>(keyword, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Keyword>> getAllKeywords() {
        List<Keyword> keywords = keywordService.getAllKeywords();
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Keyword> updateKeyword(@PathVariable Long id, @RequestBody Keyword updatedKeyword) {
        try {
            Keyword existingKeyword = keywordService.getKeywordById(id);
            existingKeyword.setKeyword(updatedKeyword.getKeyword());
            Keyword savedKeyword = keywordService.saveKeyword(existingKeyword);
            return new ResponseEntity<>(savedKeyword, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long id) {
        try {
            keywordService.deleteKeyword(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
