package com.example.modularquizappcommon.service;


import com.example.modularquizappcommon.dto.AnswerQuestionsRequest;
import com.example.modularquizappcommon.entity.*;
import com.example.modularquizappcommon.repository.AnswerRepository;
import com.example.modularquizappcommon.repository.QuestionOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuizService quizService;
    private final QuestionService questionService;


    public void save(Answer answer) {
        LocalDateTime now = LocalDateTime.now();
        answer.setDateTime(now);
        answerRepository.save(answer);
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public List<Integer> getQuestionOptionsIdByUserIdAndQuizId(int userId, int quizId) {
        return answerRepository.getAllQuestionOptionsIdByUserIdAndQuizId(userId, quizId);
    }

    public void deleteAnswerByQuizId(int quizId) {
        answerRepository.deleteAllByQuizId(quizId);
    }

    public Map<String, String> getQuizzesAndMarks(User user) {
        Map<String, Integer> quizzesAndMarks = new HashMap<>();
        for (Quiz quiz : quizService.findAll()) {
            String title = quiz.getTitle();
            List<Integer> questionOptionsId = getQuestionOptionsIdByUserIdAndQuizId(user.getId(), quiz.getId());
            int score = getScore(questionOptionsId);
            quizzesAndMarks.put(title, score);
        }

        Map<String, Integer> generalAssessments = questionService.getGeneralAssessmentsOfQuizzes();

        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Integer> quizAndMark : quizzesAndMarks.entrySet()) {
            for (Map.Entry<String, Integer> generalAssessment : generalAssessments.entrySet()) {
                if (generalAssessment.getKey().equals(quizAndMark.getKey())) {
                    result.put(quizAndMark.getKey(), quizAndMark.getValue() + " is " + generalAssessment.getValue());
                    break;
                }
            }
        }

        return result;
    }

    public List<Answer> addAnswer(AnswerQuestionsRequest answerQuestionsRequest, User user) {
        List<QuestionOption> questionOptions = getQuestionOptions(answerQuestionsRequest);
        List<Answer> answers = new ArrayList<>();
        for (QuestionOption questionOption : questionOptions) {
            Answer answer = Answer.builder()
                    .user(user)
                    .question(questionOption.getQuestion())
                    .questionOption(questionOption)
                    .build();
            save(answer);
            answers.add(answer);
        }
        return answers;
    }

    public int getScore(List<Integer> questionOptionsId) {
        int result = 0;
        List<Integer> ifHave = new ArrayList<>();
        for (Integer integer : questionOptionsId) {

            QuestionOption questionOption = questionOptionRepository.getReferenceById(integer);
            int score = questionOption.getQuestion().getScore();
            QuestionType type = questionOption.getQuestion().getType();

            if (questionOption.getIsCorrect() & type.name().equals("SINGLE_SELECT") & !ifHave.contains(integer)) {
                result += score;
                ifHave.add(integer);
            } else if (type.name().equals("MULTI_SELECT") & !ifHave.contains(integer)) {
                List<Integer> correctQuestionOptionsId = questionOptionRepository.getCorrectQuestionOptionsId(questionOption.getQuestion().getId());
                if (questionOptionsId.containsAll(correctQuestionOptionsId)) {
                    result += score;
                    ifHave.addAll(correctQuestionOptionsId);
                }
            }
        }
        return result;
    }

    private List<QuestionOption> getQuestionOptions(AnswerQuestionsRequest answerQuestionsRequest) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        List<Integer> questionOptionsId = answerQuestionsRequest.getQuestionOptionsId();
        if (questionOptionsId == null) {
            return new ArrayList<>();
        }
        for (Integer integer : questionOptionsId) {
            questionOptions.add(questionOptionRepository.getReferenceById(integer));
        }
        return questionOptions;
    }


}
