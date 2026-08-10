package com.example.backend.controller;

import com.example.backend.dto.response.VocabularyDto;
import com.example.backend.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VocabularyController {
    
    private final VocabularyService vocabularyService;

    @GetMapping("/units/{unitId}/vocabularies")
    public ResponseEntity<List<VocabularyDto>> getVocabulariesByUnitId(@PathVariable Integer unitId) {
        return ResponseEntity.ok(vocabularyService.getVocabulariesByUnitId(unitId));
    }

    @GetMapping("/vocabularies/{id}")
    public ResponseEntity<VocabularyDto> getVocabularyById(@PathVariable Integer id) {
        return ResponseEntity.ok(vocabularyService.getVocabularyById(id));
    }

    @GetMapping("/vocabularies/search")
    public ResponseEntity<List<VocabularyDto>> searchVocabularies(@RequestParam String keyword) {
        return ResponseEntity.ok(vocabularyService.searchVocabularies(keyword));
    }
}
