package com.example.backend.controller;

import com.example.backend.entity.Question;
import com.example.backend.entity.Unit;
import com.example.backend.entity.Vocabulary;
import com.example.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    // --- Unit CRUD ---
    @PostMapping("/units")
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        return ResponseEntity.ok(adminService.createUnit(unit));
    }

    @PutMapping("/units/{id}")
    public ResponseEntity<Unit> updateUnit(@PathVariable Integer id, @RequestBody Unit unit) {
        return ResponseEntity.ok(adminService.updateUnit(id, unit));
    }

    @DeleteMapping("/units/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Integer id) {
        adminService.deleteUnit(id);
        return ResponseEntity.ok().build();
    }

    // --- Vocabulary CRUD ---
    @PostMapping("/units/{unitId}/vocabularies")
    public ResponseEntity<Vocabulary> createVocabulary(@PathVariable Integer unitId, @RequestBody Vocabulary voc) {
        return ResponseEntity.ok(adminService.createVocabulary(unitId, voc));
    }

    @PutMapping("/vocabularies/{id}")
    public ResponseEntity<Vocabulary> updateVocabulary(@PathVariable Integer id, @RequestBody Vocabulary voc) {
        return ResponseEntity.ok(adminService.updateVocabulary(id, voc));
    }

    @DeleteMapping("/vocabularies/{id}")
    public ResponseEntity<Void> deleteVocabulary(@PathVariable Integer id) {
        adminService.deleteVocabulary(id);
        return ResponseEntity.ok().build();
    }

    // --- Question CRUD ---
    @PostMapping("/units/{unitId}/vocabularies/{vocabId}/questions")
    public ResponseEntity<Question> createQuestion(@PathVariable Integer unitId, @PathVariable Integer vocabId, @RequestBody Question q) {
        return ResponseEntity.ok(adminService.createQuestion(unitId, vocabId, q));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question q) {
        return ResponseEntity.ok(adminService.updateQuestion(id, q));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }
}
