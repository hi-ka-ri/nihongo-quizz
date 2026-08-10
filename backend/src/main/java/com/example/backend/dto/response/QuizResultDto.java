package com.example.backend.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class QuizResultDto {
    private Integer attemptId;
    private BigDecimal score;
    private Double percentage;
    private Integer correctCount;
    private Integer wrongCount;
    private List<VocabularyDto> wrongVocabularies;
}
