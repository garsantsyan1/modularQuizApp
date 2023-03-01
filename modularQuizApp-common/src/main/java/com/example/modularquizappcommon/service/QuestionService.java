package com.example.modularquizappcommon.service;


import com.example.modularquizappcommon.dto.CreateQuestionRequest;
import com.example.modularquizappcommon.entity.Question;
import com.example.modularquizappcommon.entity.QuestionOption;
import com.example.modularquizappcommon.entity.QuestionType;
import com.example.modularquizappcommon.entity.Quiz;
import com.example.modularquizappcommon.repository.QuestionOptionRepository;
import com.example.modularquizappcommon.repository.QuestionRepository;
import com.example.modularquizappcommon.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuizRepository quizRepository;


    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return questionRepository.existsById(id);
    }

    public List<Question> getAllQuestionsByQuizId(int quizId) {
        return questionRepository.findAllByQuiz_Id(quizId);
    }


    public Question addQuestion(CreateQuestionRequest createQuestionRequest) {
        List<QuestionOption> questionOptions = getQuestionOptions(createQuestionRequest);
        QuestionType questionType = getQuestionType(questionOptions);
        Question question = getQuestion(createQuestionRequest, questionOptions, questionType);
        saveQuestionOption(questionOptions, question);
        questionRepository.save(question);
        return question;
    }


    public Map<String, Integer> getGeneralAssessmentsOfQuizzes() {
        Map<String, Integer> generalAssessmentsOfQuizzes = new HashMap<>();
        int score = 0;
        for (Quiz quiz : quizRepository.findAll()) {
            List<Question> questionsByQuizId = questionRepository.getQuestionsByQuizId(quiz.getId());
            for (Question question : questionsByQuizId) {
                score += question.getScore();
            }
            generalAssessmentsOfQuizzes.put(quiz.getTitle(), score);
            score = 0;
        }

        return generalAssessmentsOfQuizzes;
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
