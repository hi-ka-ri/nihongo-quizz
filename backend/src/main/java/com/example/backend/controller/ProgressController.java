package com.example.backend.controller;

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
