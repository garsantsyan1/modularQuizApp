package com.example.modularquizappmvc.service;

import com.example.modularquizappcommon.entity.Quiz;
import com.example.modularquizappcommon.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getById(int id) {
        return quizRepository.getReferenceById(id);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public void addQuiz(Quiz quiz) {
        LocalDateTime now = LocalDateTime.now();
        quiz.setCreatedDateTime(now);
        quizRepository.save(quiz);
    }

    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }
}