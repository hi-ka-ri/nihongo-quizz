package com.example.backend.service;

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
                .sinoVietnamese(vocab.getSinoVietnamese())
                .build();
    }
}
