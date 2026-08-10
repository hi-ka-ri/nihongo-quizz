const fs = require('fs');
const path = require('path');

const basePath = 'd:/quizz/backend/src/main/java/com/example/backend';

const dirs = [
    'dto/response',
    'service',
    'controller',
    'exception'
];

dirs.forEach(d => fs.mkdirSync(path.join(basePath, d), { recursive: true }));

const files = {
    'dto/response/UnitDto.java': `package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitDto {
    private Integer id;
    private String title;
    private String description;
    private Integer orderIndex;
}
`,
    'dto/response/VocabularyDto.java': `package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VocabularyDto {
    private Integer id;
    private Integer unitId;
    private String kanji;
    private String hiragana;
    private String romaji;
    private String meaning;
    private String exampleSentence;
    // Optional fields per requirements if needed by UI
    private String partOfSpeech;
    private String audioUrl;
}
`,
    'exception/ResourceNotFoundException.java': `package com.example.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
`,
    'exception/GlobalExceptionHandler.java': `package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
`,
    'service/UnitService.java': `package com.example.backend.service;

import com.example.backend.dto.response.UnitDto;
import com.example.backend.entity.Unit;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {
    
    private final UnitRepository unitRepository;

    public List<UnitDto> getAllUnits() {
        return unitRepository.findAllByOrderByOrderIndexAsc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UnitDto getUnitById(Integer id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
        return mapToDto(unit);
    }

    private UnitDto mapToDto(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .title(unit.getTitle())
                .description(unit.getDescription())
                .orderIndex(unit.getOrderIndex())
                .build();
    }
}
`,
    'service/VocabularyService.java': `package com.example.backend.service;

import com.example.backend.dto.response.VocabularyDto;
import com.example.backend.entity.Vocabulary;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    
    private final VocabularyRepository vocabularyRepository;

    public List<VocabularyDto> getVocabulariesByUnitId(Integer unitId) {
        return vocabularyRepository.findByUnitId(unitId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public VocabularyDto getVocabularyById(Integer id) {
        Vocabulary vocab = vocabularyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vocabulary not found with id: " + id));
        return mapToDto(vocab);
    }

    public List<VocabularyDto> searchVocabularies(String keyword) {
        return vocabularyRepository.findAll().stream()
                .filter(v -> v.getMeaning().toLowerCase().contains(keyword.toLowerCase()) ||
                             v.getRomaji().toLowerCase().contains(keyword.toLowerCase()) ||
                             v.getHiragana().contains(keyword) ||
                             (v.getKanji() != null && v.getKanji().contains(keyword)))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private VocabularyDto mapToDto(Vocabulary vocab) {
        return VocabularyDto.builder()
                .id(vocab.getId())
                .unitId(vocab.getUnit().getId())
                .kanji(vocab.getKanji())
                .hiragana(vocab.getHiragana())
                .romaji(vocab.getRomaji())
                .meaning(vocab.getMeaning())
                .exampleSentence(vocab.getExampleSentence())
                .build();
    }
}
`,
    'controller/UnitController.java': `package com.example.backend.controller;

import com.example.backend.dto.response.UnitDto;
import com.example.backend.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {
    
    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitDto>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnitById(@PathVariable Integer id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }
}
`,
    'controller/VocabularyController.java': `package com.example.backend.controller;

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
`
};

for (const [name, content] of Object.entries(files)) {
    fs.writeFileSync(path.join(basePath, name), content);
}
console.log("Task 06 classes generated.");
