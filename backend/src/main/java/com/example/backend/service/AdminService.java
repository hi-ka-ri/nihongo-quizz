package com.example.backend.service;

import com.example.backend.dto.response.UnitDto;
import com.example.backend.entity.Question;
import com.example.backend.entity.Unit;
import com.example.backend.entity.Vocabulary;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalUnits", unitRepository.count());
        stats.put("totalVocabularies", vocabularyRepository.count());
        stats.put("totalQuestions", questionRepository.count());
        stats.put("totalQuizAttempts", quizAttemptRepository.count());
        return stats;
    }

    // --- Unit CRUD ---
    @Transactional
    public Unit createUnit(Unit unit) {
        if (unit.getImageUrl() == null || unit.getImageUrl().trim().isEmpty()) {
            unit.setImageUrl("/assets/hikari_logo.png");
        }
        return unitRepository.save(unit);
    }

    @Transactional
    public Unit updateUnit(Integer id, Unit updated) {
        Unit unit = unitRepository.findById(id).orElseThrow();
        unit.setTitle(updated.getTitle());
        unit.setDescription(updated.getDescription());
        unit.setOrderIndex(updated.getOrderIndex());
        return unitRepository.save(unit);
    }

    @Transactional
    public void deleteUnit(Integer id) {
        unitRepository.deleteById(id);
    }

    // --- Vocabulary CRUD ---
    @Transactional
    public Vocabulary createVocabulary(Integer unitId, Vocabulary voc) {
        Unit unit = unitRepository.findById(unitId).orElseThrow();
        voc.setUnit(unit);
        return vocabularyRepository.save(voc);
    }

    @Transactional
    public Vocabulary updateVocabulary(Integer id, Vocabulary updated) {
        Vocabulary voc = vocabularyRepository.findById(id).orElseThrow();
        voc.setKanji(updated.getKanji());
        voc.setHiragana(updated.getHiragana());
        voc.setRomaji(updated.getRomaji());
        voc.setMeaning(updated.getMeaning());
        voc.setExampleSentence(updated.getExampleSentence());
        voc.setSinoVietnamese(updated.getSinoVietnamese());
        // Handle changing unit if needed, but omitted for simplicity
        return vocabularyRepository.save(voc);
    }

    @Transactional
    public void deleteVocabulary(Integer id) {
        vocabularyRepository.deleteById(id);
    }

    // --- Question CRUD ---
    @Transactional
    public Question createQuestion(Integer unitId, Integer vocabId, Question q) {
        Unit unit = unitRepository.findById(unitId).orElseThrow();
        Vocabulary voc = vocabularyRepository.findById(vocabId).orElseThrow();
        q.setUnit(unit);
        q.setVocabulary(voc);
        return questionRepository.save(q);
    }

    @Transactional
    public Question updateQuestion(Integer id, Question updated) {
        Question q = questionRepository.findById(id).orElseThrow();
        q.setQuestionType(updated.getQuestionType());
        q.setQuestionText(updated.getQuestionText());
        q.setOptionA(updated.getOptionA());
        q.setOptionB(updated.getOptionB());
        q.setOptionC(updated.getOptionC());
        q.setOptionD(updated.getOptionD());
        q.setCorrectAnswer(updated.getCorrectAnswer());
        return questionRepository.save(q);
    }

    @Transactional
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
