package com.example.backend.dto.request;
import lombok.Data;
@Data
public class AnswerRequest {
    private Integer questionId;
    private String selectedAnswer;
}
