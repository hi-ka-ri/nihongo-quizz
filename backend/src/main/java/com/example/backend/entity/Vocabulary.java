package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vocabularies", indexes = {
    @Index(columnList = "unit_id")
})
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

    @Column(length = 100)
    private String sinoVietnamese;

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
