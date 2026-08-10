package com.example.backend.controller;

import com.example.backend.dto.request.AnswerRequest;
import com.example.backend.dto.request.StartQuizRequest;
import com.example.backend.dto.response.QuestionDto;
import com.example.backend.dto.response.QuizAttemptDto;
import com.example.backend.dto.response.QuizResultDto;
import com.example.backend.security.UserDetailsImpl;
import com.example.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/units/{unitId}/quiz")
    public ResponseEntity<List<QuestionDto>> getQuizForUnit(@PathVariable Integer unitId) {
        return ResponseEntity.ok(quizService.getQuestionsForUnit(unitId));
    }

    @PostMapping("/quiz/attempts")
    public ResponseEntity<QuizAttemptDto> startAttempt(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody StartQuizRequest request) {
        Integer userId = userDetails != null ? userDetails.getUser().getId() : null;
        return ResponseEntity.ok(quizService.startAttempt(userId, request.getQuizId()));
    }

    @PostMapping("/quiz/attempts/{attemptId}/answers")
    public ResponseEntity<Void> submitAnswer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer attemptId,
            @RequestBody AnswerRequest request) {
        Integer userId = userDetails != null ? userDetails.getUser().getId() : null;
        quizService.submitAnswer(userId, attemptId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quiz/attempts/{attemptId}/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer attemptId) {
        Integer userId = userDetails != null ? userDetails.getUser().getId() : null;
        return ResponseEntity.ok(quizService.submitQuiz(userId, attemptId));
    }

    @PostMapping("/quiz/review")
    public ResponseEntity<QuizAttemptDto> startReviewQuiz(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer userId = userDetails != null ? userDetails.getUser().getId() : null;
        return ResponseEntity.ok(quizService.startReviewAttempt(userId));
    }
}
