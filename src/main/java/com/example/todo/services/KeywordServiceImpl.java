package com.example.todo.services;

import com.example.todo.models.Keyword;
import com.example.todo.repositories.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public Keyword saveKeyword(Keyword keyword) {
        keyword.setKeyword(keyword.getKeyword().toLowerCase());
        Keyword existingKeyword = keywordRepository.findByKeyword(keyword.getKeyword());
        if (existingKeyword != null) {
            return existingKeyword;
        }
        return keywordRepository.save(keyword);
    }

    @Override
    public Keyword getKeywordById(Long id) {
        return keywordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keyword not found with id: " + id));
    }

    @Override
    public Keyword getKeywordByText(String keywordText) {
        keywordText = keywordText.toLowerCase();
        return keywordRepository.findByKeyword(keywordText);
    }

    @Override
    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    @Override
    public void deleteKeyword(Long id) {
        if (keywordRepository.existsById(id)) {
            keywordRepository.deleteById(id);
        } else {
            throw new RuntimeException("Keyword not found with id: " + id);
        }
    }
}
