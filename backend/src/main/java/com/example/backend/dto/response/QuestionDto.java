package com.example.backend.dto.response;
import com.example.backend.entity.enums.QuestionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDto {
    private Integer id;
    private QuestionType questionType;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private VocabularyDto vocabulary;
}
