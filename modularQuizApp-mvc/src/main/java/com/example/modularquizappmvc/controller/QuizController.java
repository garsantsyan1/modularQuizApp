package com.example.modularquizappmvc.controller;


import com.example.modularquizappcommon.entity.Quiz;
import com.example.modularquizappmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quiz/add")
    public String addQuiz() {
        return "createQuiz";
    }

    @PostMapping("/quiz/add")
    public String addQuiz(@ModelAttribute Quiz quiz) {
        quizService.addQuiz(quiz);
        return "redirect:/teach/page";
    }

    @GetMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable("id") int id) {
        quizService.deleteById(id);
        return "redirect:/teach/page";
    }

}
