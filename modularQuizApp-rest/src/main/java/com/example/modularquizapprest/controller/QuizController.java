package com.example.modularquizapprest.controller;


import com.example.modularquizappcommon.entity.Quiz;
import com.example.modularquizappcommon.service.QuizService;
import com.example.modularquizapprest.dto.QuizRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final ModelMapper modelMapper;
    private final QuizService quizService;

    @GetMapping("/quizzes/")
    public List<Quiz> allQuizzes() {
        return quizService.findAll();
    }


    @PostMapping("/quiz/add")
    public ResponseEntity<Quiz> addQuiz(@RequestBody QuizRequestDto quizRequestDto) {
        Quiz map = modelMapper.map(quizRequestDto, Quiz.class);
        Quiz quiz = quizService.addQuiz(map);
        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/deleteQuiz/{id}")
    public ResponseEntity deleteQuiz(@PathVariable("id") int id) {
        if (quizService.existsById(id)) {
            quizService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}