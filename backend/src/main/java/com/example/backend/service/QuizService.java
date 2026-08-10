package com.example.backend.service;

import com.example.backend.dto.request.AnswerRequest;
import com.example.backend.dto.response.QuestionDto;
import com.example.backend.dto.response.QuizAttemptDto;
import com.example.backend.dto.response.QuizResultDto;
import com.example.backend.dto.response.VocabularyDto;
import com.example.backend.entity.*;
import com.example.backend.entity.enums.ProgressStatus;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final QuizAnswerRepository answerRepository;
    private final UserVocabularyProgressRepository progressRepository;
    private final UserRepository userRepository;

    public List<QuestionDto> getQuestionsForUnit(Integer unitId) {
        List<Question> questions = questionRepository.findByUnitId(unitId);
        // We can shuffle the questions order
        Collections.shuffle(questions);
        
        return questions.stream().map(q -> {
            // Note: Options are already stored in the DB (optionA, optionB, etc).
            // We do not send correctAnswer to the frontend.
            return QuestionDto.builder()
                    .id(q.getId())
                    .questionType(q.getQuestionType())
                    .questionText(q.getQuestionText())
                    .optionA(q.getOptionA())
                    .optionB(q.getOptionB())
                    .optionC(q.getOptionC())
                    .optionD(q.getOptionD())
                    .correctAnswer(q.getCorrectAnswer())
                    .vocabulary(mapToDto(q.getVocabulary()))
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public QuizAttemptDto startAttempt(Integer userId, Integer quizId) {
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }
        
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        List<Question> questions = questionRepository.findByUnitId(quiz.getUnit().getId());
        
        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .totalQuestions(questions.size())
                .startTime(LocalDateTime.now())
                .build();
        
        attempt = attemptRepository.save(attempt);
        
        return QuizAttemptDto.builder()
                .id(attempt.getId())
                .quizId(quiz.getId())
                .totalQuestions(attempt.getTotalQuestions())
                .startTime(attempt.getStartTime())
                .build();
    }

    @Transactional
    public void submitAnswer(Integer userId, Integer attemptId, AnswerRequest request) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found"));
                
        if (userId != null) {
            if (attempt.getUser() == null || !attempt.getUser().getId().equals(userId)) {
                throw new RuntimeException("Unauthorized");
            }
        } else {
            if (attempt.getUser() != null) {
                throw new RuntimeException("Unauthorized");
            }
        }
        
        if (attempt.getEndTime() != null) {
            throw new RuntimeException("Quiz already submitted");
        }

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        // Check if an answer already exists for this attempt and question
        List<QuizAnswer> existing = answerRepository.findByAttemptId(attemptId);
        boolean exists = existing.stream().anyMatch(a -> a.getQuestion().getId().equals(question.getId()));
        if (exists) {
            // You can choose to update it or throw an error. Let's update or ignore.
            // For simplicity, we just save a new one or you could replace.
            // Actually let's just let them add it, or better: prevent duplicates.
            return;
        }

        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(request.getSelectedAnswer());
        
        QuizAnswer answer = QuizAnswer.builder()
                .attempt(attempt)
                .question(question)
                .selectedOption(request.getSelectedAnswer())
                .isCorrect(isCorrect)
                .build();
                
        answerRepository.save(answer);
    }

    @Transactional
    public QuizResultDto submitQuiz(Integer userId, Integer attemptId) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found"));

        if (userId != null) {
            if (attempt.getUser() == null || !attempt.getUser().getId().equals(userId)) {
                throw new RuntimeException("Unauthorized");
            }
        } else {
            if (attempt.getUser() != null) {
                throw new RuntimeException("Unauthorized");
            }
        }
        
        if (attempt.getEndTime() != null) {
            throw new RuntimeException("Quiz already submitted");
        }

        attempt.setEndTime(LocalDateTime.now());
        
        List<QuizAnswer> answers = answerRepository.findByAttemptId(attemptId);
        
        int correctCount = 0;
        int wrongCount = 0;
        List<VocabularyDto> wrongVocabs = new ArrayList<>();
        
        for (QuizAnswer answer : answers) {
            if (Boolean.TRUE.equals(answer.getIsCorrect())) {
                correctCount++;
                if (userId != null) updateProgress(userId, answer.getQuestion().getVocabulary(), true);
            } else {
                wrongCount++;
                Vocabulary v = answer.getQuestion().getVocabulary();
                if (userId != null) updateProgress(userId, v, false);
                wrongVocabs.add(mapToDto(v));
            }
        }
        
        // Unanswered questions are wrong
        wrongCount += (attempt.getTotalQuestions() - answers.size());
        
        double percent = attempt.getTotalQuestions() == 0 ? 0 : 
            ((double) correctCount / attempt.getTotalQuestions()) * 100;
            
        BigDecimal score = BigDecimal.valueOf(correctCount * 10); // Example scoring: 10 pts per question
        
        attempt.setScore(score);
        attemptRepository.save(attempt);
        
        return QuizResultDto.builder()
                .attemptId(attempt.getId())
                .score(score)
                .percentage(percent)
                .correctCount(correctCount)
                .wrongCount(wrongCount)
                .wrongVocabularies(wrongVocabs)
                .build();
    }
    
    private void updateProgress(Integer userId, Vocabulary vocab, boolean isCorrect) {
        UserVocabularyProgress progress = progressRepository.findByUserIdAndVocabularyId(userId, vocab.getId())
                .orElse(UserVocabularyProgress.builder()
                        .user(userRepository.getReferenceById(userId))
                        .vocabulary(vocab)
                        .status(ProgressStatus.LEARNING)
                        .failedCount(0)
                        .correctCount(0)
                        .build());
                        
        if (isCorrect) {
            progress.setCorrectCount(progress.getCorrectCount() + 1);
            if (progress.getCorrectCount() >= 3 && progress.getFailedCount() == 0) {
                progress.setStatus(ProgressStatus.MASTERED);
            }
        } else {
            progress.setFailedCount(progress.getFailedCount() + 1);
            progress.setStatus(ProgressStatus.NEEDS_REVIEW);
        }
        progress.setLastReviewedAt(LocalDateTime.now());
        
        progressRepository.save(progress);
    }

    private VocabularyDto mapToDto(Vocabulary vocab) {
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

    @Transactional
    public QuizAttemptDto startReviewAttempt(Integer userId) {
        if (userId == null) {
            throw new RuntimeException("Guests cannot start a review attempt");
        }
        User user = userRepository.findById(userId).orElseThrow();
        
        // Find review questions (where failedCount > 0 or NEEDS_REVIEW)
        List<UserVocabularyProgress> wrongVocabs = progressRepository.findByUserIdAndStatus(userId, ProgressStatus.NEEDS_REVIEW);
        
        // Ensure there is at least a "Review" quiz in DB or let's find the first quiz available, 
        // ideally we would create a temporary Quiz or make quiz nullable. 
        // For simplicity, we just use any quiz or unit 1's quiz as placeholder,
        // or let's create a dedicated review Quiz if it doesn't exist.
        Quiz reviewQuiz = quizRepository.findById(-1).orElseGet(() -> {
            Unit u = new Unit();
            u.setTitle("Review Unit");
            // Need to save Unit first but we assume it exists or just make quiz nullable in DB.
            return quizRepository.findAll().stream().findFirst().orElseThrow();
        });

        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(reviewQuiz)
                .totalQuestions(wrongVocabs.size())
                .startTime(LocalDateTime.now())
                .build();
        
        attempt = attemptRepository.save(attempt);
        
        return QuizAttemptDto.builder()
                .id(attempt.getId())
                .quizId(reviewQuiz.getId())
                .totalQuestions(attempt.getTotalQuestions())
                .startTime(attempt.getStartTime())
                .build();
    }
}
