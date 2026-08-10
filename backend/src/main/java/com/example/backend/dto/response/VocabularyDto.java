package com.example.backend.dto.response;

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
    private String sinoVietnamese;
    // Optional fields per requirements if needed by UI
    private String partOfSpeech;
    private String audioUrl;
}
