package com.example.backend.service;

import com.example.backend.dto.response.DashboardDto;
import com.example.backend.dto.response.ProgressDto;
import com.example.backend.dto.response.VocabularyDto;
import com.example.backend.entity.enums.ProgressStatus;
import com.example.backend.repository.QuizAttemptRepository;
import com.example.backend.repository.UnitRepository;
import com.example.backend.repository.UserVocabularyProgressRepository;
import com.example.backend.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserVocabularyProgressRepository progressRepository;
    private final UnitRepository unitRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuizAttemptRepository attemptRepository;

    public List<ProgressDto> getProgress(Integer userId) {
        return progressRepository.findByUserId(userId).stream()
                .map(p -> ProgressDto.builder()
                        .vocabularyId(p.getVocabulary().getId())
                        .status(p.getStatus())
                        .correctCount(p.getCorrectCount())
                        .failedCount(p.getFailedCount())
                        .lastReviewedAt(p.getLastReviewedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProgressDto> getProgressForUnit(Integer userId, Integer unitId) {
        return progressRepository.findByUserIdAndUnitId(userId, unitId).stream()
                .map(p -> ProgressDto.builder()
                        .vocabularyId(p.getVocabulary().getId())
                        .status(p.getStatus())
                        .correctCount(p.getCorrectCount())
                        .failedCount(p.getFailedCount())
                        .lastReviewedAt(p.getLastReviewedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<VocabularyDto> getReviewVocabularies(Integer userId) {
        return progressRepository.findByUserIdAndStatus(userId, ProgressStatus.NEEDS_REVIEW).stream()
                .map(p -> mapToVocabDto(p.getVocabulary()))
                .collect(Collectors.toList());
    }
    
    public List<VocabularyDto> getWrongVocabularies(Integer userId) {
        return progressRepository.findWrongVocabularies(userId).stream()
                .map(p -> mapToVocabDto(p.getVocabulary()))
                .collect(Collectors.toList());
    }

    public DashboardDto getDashboard(Integer userId) {
        long totalUnits = unitRepository.count();
        long totalVocab = vocabularyRepository.count();
        long learnedVocab = progressRepository.countByUserIdAndStatus(userId, ProgressStatus.MASTERED);
        long reviewVocab = progressRepository.countByUserIdAndStatus(userId, ProgressStatus.NEEDS_REVIEW);
        long quizzesTaken = attemptRepository.countByUserId(userId);
        BigDecimal averageScore = attemptRepository.findAverageScoreByUserId(userId);
        
        if (averageScore == null) {
            averageScore = BigDecimal.ZERO;
        }

        return DashboardDto.builder()
                .totalUnits(totalUnits)
                .completedUnits(0) // Requires complex calculation or separate entity, keeping simple for now
                .totalVocabularies(totalVocab)
                .learnedVocabularies(learnedVocab)
                .reviewVocabularies(reviewVocab)
                .quizzesTaken(quizzesTaken)
                .averageScore(averageScore)
                .build();
    }

    private VocabularyDto mapToVocabDto(com.example.backend.entity.Vocabulary vocab) {
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
