package com.example.backend.repository;

import com.example.backend.entity.UserVocabularyProgress;
import com.example.backend.entity.enums.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserVocabularyProgressRepository extends JpaRepository<UserVocabularyProgress, Integer> {
    List<UserVocabularyProgress> findByUserId(Integer userId);
    Optional<UserVocabularyProgress> findByUserIdAndVocabularyId(Integer userId, Integer vocabularyId);
    List<UserVocabularyProgress> findByUserIdAndStatus(Integer userId, ProgressStatus status);
    
    @org.springframework.data.jpa.repository.Query("SELECT p FROM UserVocabularyProgress p WHERE p.user.id = :userId AND p.vocabulary.unit.id = :unitId")
    List<UserVocabularyProgress> findByUserIdAndUnitId(@org.springframework.data.repository.query.Param("userId") Integer userId, @org.springframework.data.repository.query.Param("unitId") Integer unitId);
    
    @org.springframework.data.jpa.repository.Query("SELECT p FROM UserVocabularyProgress p WHERE p.user.id = :userId AND p.failedCount > 0")
    List<UserVocabularyProgress> findWrongVocabularies(@org.springframework.data.repository.query.Param("userId") Integer userId);
    
    long countByUserIdAndStatus(Integer userId, ProgressStatus status);
}
