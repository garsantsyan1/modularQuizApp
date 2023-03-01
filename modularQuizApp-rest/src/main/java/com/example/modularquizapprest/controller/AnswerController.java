package com.example.modularquizapprest.controller;


import com.example.modularquizappcommon.dto.AnswerQuestionsRequest;
import com.example.modularquizappcommon.entity.Answer;
import com.example.modularquizappcommon.security.CurrentUser;
import com.example.modularquizappcommon.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/answer")
    public ResponseEntity<List<Answer>> answer(@RequestBody AnswerQuestionsRequest answerQuestionsRequest, @AuthenticationPrincipal CurrentUser currentUser) {
        if (!answerService.findAll().isEmpty()) {
            answerService.deleteAnswerByQuizId(answerQuestionsRequest.getQuizId());
        }

        List<Answer> answers = answerService.addAnswer(answerQuestionsRequest, currentUser.getUser());
        return ResponseEntity.ok(answers);
    }

}
