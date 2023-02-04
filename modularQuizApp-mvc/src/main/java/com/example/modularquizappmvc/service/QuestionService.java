package com.example.modularquizappmvc.service;


import com.example.modularquizappcommon.dto.CreateQuestionRequest;
import com.example.modularquizappcommon.entity.Question;
import com.example.modularquizappcommon.entity.QuestionOption;
import com.example.modularquizappcommon.entity.QuestionType;
import com.example.modularquizappcommon.repository.QuestionOptionRepository;
import com.example.modularquizappcommon.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;


    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }

    public List<Question> getAllQuestions(int quizId) {
        return questionRepository.findAllByQuiz_Id(quizId);
    }


    public void addQuestion(CreateQuestionRequest createQuestionRequest) {
        List<QuestionOption> questionOptions = getQuestionOptions(createQuestionRequest);
        QuestionType questionType = getQuestionType(questionOptions);
        Question question = getQuestion(createQuestionRequest, questionOptions, questionType);
        saveQuestionOption(questionOptions, question);
        questionRepository.save(question);
    }

    private Question getQuestion(CreateQuestionRequest createQuestionRequest, List<QuestionOption> questionOptions, QuestionType questionType) {

        return Question.builder()
                .type(questionType)
                .title(createQuestionRequest.getTitle())
                .score(createQuestionRequest.getScore())
                .quiz(createQuestionRequest.getQuiz())
                .questionOptions(questionOptions)
                .build();
    }

    private void saveQuestionOption(List<QuestionOption> questionOptions, Question question) {
        for (QuestionOption questionOption : questionOptions) {
            questionOption.setQuestion(question);
            questionOptionRepository.save(questionOption);
        }
    }

    private QuestionType getQuestionType(List<QuestionOption> questionOptions) {
        int select = 0;
        QuestionType questionType = null;
        for (QuestionOption questionOption : questionOptions) {
            if (questionOption.getIsCorrect()) {
                select++;
            }
            if (select > 1) {
                questionType = QuestionType.MULTI_SELECT;
            } else {
                questionType = QuestionType.SINGLE_SELECT;
            }
        }
        return questionType;
    }

    private List<QuestionOption> getQuestionOptions(CreateQuestionRequest createQuestionRequest) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        questionOptions.add(createQuestionRequest.getQuestionOption1());
        questionOptions.add(createQuestionRequest.getQuestionOption2());
        questionOptions.add(createQuestionRequest.getQuestionOption3());
        questionOptions.add(createQuestionRequest.getQuestionOption4());
        for (QuestionOption questionOption : questionOptions) {
            if (questionOption.getIsCorrect() == null) {
                questionOption.setIsCorrect(false);
            }
        }
        return questionOptions;
    }
}
