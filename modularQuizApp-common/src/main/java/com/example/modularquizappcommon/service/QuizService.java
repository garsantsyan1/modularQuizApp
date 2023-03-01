package com.example.modularquizappcommon.service;

import com.example.modularquizappcommon.entity.Quiz;
import com.example.modularquizappcommon.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;


    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Quiz addQuiz(Quiz quiz) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String now = LocalDateTime.now().format(formatter);
        LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);
        quiz.setCreatedDateTime(formatDateTime);
        quizRepository.save(quiz);
        return quiz;
    }

    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return quizRepository.existsById(id);
    }

}
