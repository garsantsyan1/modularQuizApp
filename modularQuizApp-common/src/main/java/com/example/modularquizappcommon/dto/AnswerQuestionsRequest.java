package com.example.modularquizappcommon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerQuestionsRequest {

    private int quizId;
    private List<Integer> questionsId;
    private List<Integer> questionOptionsId;

}
