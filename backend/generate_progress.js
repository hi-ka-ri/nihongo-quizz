const fs = require('fs');
const path = require('path');

const basePath = 'd:/quizz/backend/src/main/java/com/example/backend';
const dirs = ['dto/response', 'service', 'controller'];
dirs.forEach(d => fs.mkdirSync(path.join(basePath, d), { recursive: true }));

const files = {
    'dto/response/DashboardDto.java': `package com.example.backend.dto.response;
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
`,
    'dto/response/ProgressDto.java': `package com.example.backend.dto.response;
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
`,
    'service/ProgressService.java': `package com.example.backend.service;

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
                .build();
    }
}
`,
    'controller/ProgressController.java': `package com.example.backend.controller;

import com.example.backend.dto.response.ProgressDto;
import com.example.backend.dto.response.VocabularyDto;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/progress")
    public ResponseEntity<List<ProgressDto>> getProgress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getProgress(userDetails.getUser().getId()));
    }

    @GetMapping("/progress/unit/{unitId}")
    public ResponseEntity<List<ProgressDto>> getProgressForUnit(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer unitId) {
        return ResponseEntity.ok(progressService.getProgressForUnit(userDetails.getUser().getId(), unitId));
    }

    @GetMapping("/vocabularies/review")
    public ResponseEntity<List<VocabularyDto>> getReviewVocabularies(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getReviewVocabularies(userDetails.getUser().getId()));
    }

    @GetMapping("/vocabularies/wrong")
    public ResponseEntity<List<VocabularyDto>> getWrongVocabularies(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getWrongVocabularies(userDetails.getUser().getId()));
    }
}
`,
    'controller/DashboardController.java': `package com.example.backend.controller;

import com.example.backend.dto.response.DashboardDto;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProgressService progressService;

    @GetMapping
    public ResponseEntity<DashboardDto> getDashboard(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(progressService.getDashboard(userDetails.getUser().getId()));
    }
}
`
};

for (const [name, content] of Object.entries(files)) {
    fs.writeFileSync(path.join(basePath, name), content);
}
console.log("Task 08 progress classes generated.");
