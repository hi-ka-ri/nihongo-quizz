package com.example.backend.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardDto {
    private long totalUnits;
    private long completedUnits;
    private long totalVocabularies;
    private long learnedVocabularies;
    private long reviewVocabularies;
    private long quizzesTaken;
    private BigDecimal averageScore;
}
