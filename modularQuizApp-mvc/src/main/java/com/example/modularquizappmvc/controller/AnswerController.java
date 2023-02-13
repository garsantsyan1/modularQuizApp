package com.example.modularquizappmvc.controller;


import com.example.modularquizappcommon.dto.AnswerQuestionsRequest;
import com.example.modularquizappmvc.security.CurrentUser;
import com.example.modularquizappmvc.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;


    @PostMapping("/answer")
    public String answer(@ModelAttribute AnswerQuestionsRequest answerQuestionsRequest, ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        if (!answerService.findAll().isEmpty()) {
            answerService.deleteAnswerByQuizId(answerQuestionsRequest.getQuizId());
        }

        answerService.addAnswer(answerQuestionsRequest, currentUser.getUser());
        Map<String, String> quizzesAndMarks = answerService.getQuizzesAndMarks(currentUser.getUser());

        map.addAttribute("user", currentUser.getUser());
        map.addAttribute("quizzesAndMarks", quizzesAndMarks);

        return "quizzesResults";

    }

}
