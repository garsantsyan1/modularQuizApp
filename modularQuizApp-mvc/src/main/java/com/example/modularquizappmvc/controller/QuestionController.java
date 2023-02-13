package com.example.modularquizappmvc.controller;


import com.example.modularquizappcommon.dto.CreateQuestionRequest;
import com.example.modularquizappcommon.entity.Question;
import com.example.modularquizappmvc.security.CurrentUser;
import com.example.modularquizappmvc.service.AnswerService;
import com.example.modularquizappmvc.service.QuestionService;
import com.example.modularquizappmvc.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuizService quizService;

    private final AnswerService answerService;

    @GetMapping("/question/add")
    public String addQuiz(ModelMap map) {
        map.addAttribute("quizzes", quizService.findAll());
        if (quizService.findAll().isEmpty()) {
            return "createQuiz";
        }
        return "createQuestion";
    }

    @PostMapping("/question/add")
    public String addQuestion(@ModelAttribute CreateQuestionRequest createQuestionRequest) {
        questionService.addQuestion(createQuestionRequest);
        return "redirect:/teach/page";
    }


    @GetMapping("/questions/{id}")
    public String questions(@PathVariable("id") int id, ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Question> allQuestions = questionService.getAllQuestions(id);
        map.addAttribute("questions", allQuestions);
        map.addAttribute("user", currentUser.getUser());
        return "questions";
    }

    @GetMapping("/questionsList/{id}")
    public String questionsList(@PathVariable("id") int id, ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Question> allQuestions = questionService.getAllQuestions(id);
        map.addAttribute("questions", allQuestions);
        map.addAttribute("user", currentUser.getUser());
        return "questionsList";
    }


    @GetMapping("/deleteQuestion/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteById(id);
        return "redirect:/teach/page";
    }

    @GetMapping("/quizzesResults")
    public String quizzesResults(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        Map<String, String> quizzesAndMarks = answerService.getQuizzesAndMarks(currentUser.getUser());

        map.addAttribute("quizzesAndMarks", quizzesAndMarks);
        map.addAttribute("user", currentUser.getUser());
        return "quizzesResults";
    }

}
