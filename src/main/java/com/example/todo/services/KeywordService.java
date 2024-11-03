package com.example.todo.services;

import com.example.todo.models.Keyword;

import java.util.List;

public interface KeywordService {
    Keyword saveKeyword(Keyword keyword);
    Keyword getKeywordById(Long id);
    Keyword getKeywordByText(String keywordText);
    List<Keyword> getAllKeywords();
    void deleteKeyword(Long id);
}
