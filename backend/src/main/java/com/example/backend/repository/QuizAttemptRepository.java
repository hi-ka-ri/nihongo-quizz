package com.example.backend.repository;

import com.example.backend.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {
    List<QuizAttempt> findByUserId(Integer userId);
    List<QuizAttempt> findByUserIdAndQuizId(Integer userId, Integer quizId);
    
    long countByUserId(Integer userId);
    
    @org.springframework.data.jpa.repository.Query("SELECT AVG(q.score) FROM QuizAttempt q WHERE q.user.id = :userId AND q.score IS NOT NULL")
    java.math.BigDecimal findAverageScoreByUserId(@org.springframework.data.repository.query.Param("userId") Integer userId);
}
