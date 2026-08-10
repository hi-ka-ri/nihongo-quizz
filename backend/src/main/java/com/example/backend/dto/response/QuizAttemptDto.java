package com.example.backend.dto.response;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class QuizAttemptDto {
    private Integer id;
    private Integer quizId;
    private Integer totalQuestions;
    private LocalDateTime startTime;
}
