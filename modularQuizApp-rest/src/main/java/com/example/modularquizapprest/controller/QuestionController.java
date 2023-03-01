package com.example.modularquizapprest.controller;


import com.example.modularquizappcommon.dto.CreateQuestionRequest;
import com.example.modularquizappcommon.entity.Question;
import com.example.modularquizappcommon.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/question/add")
    public ResponseEntity<Question> addQuestion(@RequestBody CreateQuestionRequest createQuestionRequest) {
        Question question = questionService.addQuestion(createQuestionRequest);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<List<Question>> questions(@PathVariable("id") int id) {
        List<Question> allQuestions = questionService.getAllQuestionsByQuizId(id);
        return ResponseEntity.ok(allQuestions);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") int id) {
        if (questionService.existsById(id)) {
            questionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
