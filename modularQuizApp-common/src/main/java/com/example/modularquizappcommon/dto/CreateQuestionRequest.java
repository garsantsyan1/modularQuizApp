package com.example.modularquizappcommon.dto;

import com.example.modularquizappcommon.entity.QuestionOption;
import com.example.modularquizappcommon.entity.QuestionType;
import com.example.modularquizappcommon.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionRequest {

    private QuestionType type;
    private String title;
    private int score;
    private Quiz quiz;
    private QuestionOption questionOption1;
    private QuestionOption questionOption2;
    private QuestionOption questionOption3;
    private QuestionOption questionOption4;

}

