package com.example.backend.dto.response;
import com.example.backend.entity.enums.ProgressStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ProgressDto {
    private Integer vocabularyId;
    private ProgressStatus status;
    private Integer correctCount;
    private Integer failedCount;
    private LocalDateTime lastReviewedAt;
}
