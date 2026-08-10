const fs = require('fs');
const path = require('path');

const basePath = 'd:/quizz/backend/src/main/java/com/example/backend';

const entityPath = path.join(basePath, 'entity');
const repoPath = path.join(basePath, 'repository');
const enumPath = path.join(basePath, 'entity', 'enums');

fs.mkdirSync(entityPath, { recursive: true });
fs.mkdirSync(repoPath, { recursive: true });
fs.mkdirSync(enumPath, { recursive: true });

const enums = {
    'Role.java': `package com.example.backend.entity.enums;

public enum Role {
    USER, ADMIN
}
`,
    'QuestionType.java': `package com.example.backend.entity.enums;

public enum QuestionType {
    KANJI_TO_MEANING, MEANING_TO_KANJI, HIRAGANA_TO_KANJI, KANJI_TO_HIRAGANA, ROMAJI_TO_MEANING
}
`,
    'ProgressStatus.java': `package com.example.backend.entity.enums;

public enum ProgressStatus {
    LEARNING, MASTERED, NEEDS_REVIEW
}
`
};

for (const [name, content] of Object.entries(enums)) {
    fs.writeFileSync(path.join(enumPath, name), content);
}

const entities = {
    'User.java': `package com.example.backend.entity;

import com.example.backend.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"quizAttempts", "vocabularyProgresses"})
@EqualsAndHashCode(exclude = {"quizAttempts", "vocabularyProgresses"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER', 'ADMIN') DEFAULT 'USER'")
    private Role role = Role.USER;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<QuizAttempt> quizAttempts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserVocabularyProgress> vocabularyProgresses;
}
`,
    'Unit.java': `package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"vocabularies", "questions", "quizzes"})
@EqualsAndHashCode(exclude = {"vocabularies", "questions", "quizzes"})
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "order_index")
    private Integer orderIndex = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Vocabulary> vocabularies;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Quiz> quizzes;
}
`,
    'Vocabulary.java': `package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vocabularies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"questions", "progresses"})
@EqualsAndHashCode(exclude = {"questions", "progresses"})
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonIgnore
    private Unit unit;

    @Column(length = 50)
    private String kanji;

    @Column(nullable = false, length = 50)
    private String hiragana;

    @Column(nullable = false, length = 50)
    private String romaji;

    @Column(nullable = false, length = 255)
    private String meaning;

    @Column(columnDefinition = "TEXT")
    private String exampleSentence;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions;

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserVocabularyProgress> progresses;
}
`,
    'Question.java': `package com.example.backend.entity;

import com.example.backend.entity.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "quizAnswers")
@EqualsAndHashCode(exclude = "quizAnswers")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonIgnore
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    @JsonIgnore
    private Vocabulary vocabulary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false, length = 255)
    private String questionText;

    @Column(nullable = false, length = 100)
    private String optionA;

    @Column(nullable = false, length = 100)
    private String optionB;

    @Column(nullable = false, length = 100)
    private String optionC;

    @Column(nullable = false, length = 100)
    private String optionD;

    @Column(nullable = false, length = 1)
    private String correctAnswer;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<QuizAnswer> quizAnswers;
}
`,
    'Quiz.java': `package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "attempts")
@EqualsAndHashCode(exclude = "attempts")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonIgnore
    private Unit unit;

    @Column(length = 100)
    private String title;

    private Integer timeLimitSeconds = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<QuizAttempt> attempts;
}
`,
    'QuizAttempt.java': `package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quiz_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "answers")
@EqualsAndHashCode(exclude = "answers")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    private Integer totalQuestions;

    @CreationTimestamp
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<QuizAnswer> answers;
}
`,
    'QuizAnswer.java': `package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    @JsonIgnore
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;

    @Column(length = 1)
    private String selectedOption;

    private Boolean isCorrect;
}
`,
    'UserVocabularyProgress.java': `package com.example.backend.entity;

import com.example.backend.entity.enums.ProgressStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_vocabulary_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vocabulary_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVocabularyProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    @JsonIgnore
    private Vocabulary vocabulary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('LEARNING', 'MASTERED', 'NEEDS_REVIEW') DEFAULT 'LEARNING'")
    private ProgressStatus status = ProgressStatus.LEARNING;

    private Integer failedCount = 0;

    private Integer correctCount = 0;

    @UpdateTimestamp
    private LocalDateTime lastReviewedAt;
}
`
};

for (const [name, content] of Object.entries(entities)) {
    fs.writeFileSync(path.join(entityPath, name), content);
}


const repositories = {
    'UserRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
`,
    'UnitRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    List<Unit> findAllByOrderByOrderIndexAsc();
}
`,
    'VocabularyRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
    List<Vocabulary> findByUnitId(Integer unitId);
}
`,
    'QuestionRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByUnitId(Integer unitId);
}
`,
    'QuizRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> findByUnitId(Integer unitId);
}
`,
    'QuizAttemptRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {
    List<QuizAttempt> findByUserId(Integer userId);
    List<QuizAttempt> findByUserIdAndQuizId(Integer userId, Integer quizId);
}
`,
    'QuizAnswerRepository.java': `package com.example.backend.repository;

import com.example.backend.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Integer> {
    List<QuizAnswer> findByAttemptId(Integer attemptId);
}
`,
    'UserVocabularyProgressRepository.java': `package com.example.backend.repository;

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
}
`
};

for (const [name, content] of Object.entries(repositories)) {
    fs.writeFileSync(path.join(repoPath, name), content);
}
console.log("Generated classes.");
