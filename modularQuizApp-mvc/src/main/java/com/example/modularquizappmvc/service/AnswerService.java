package com.example.modularquizappmvc.service;


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

    public Map<String, Integer> getQuizzesAndMarks(User user) {
        Map<String, Integer> quizMarks = new HashMap<>();
        for (Quiz quiz : quizService.findAll()) {
            String title = quiz.getTitle();
            List<Integer> questionOptionsId = getQuestionOptionsIdByUserIdAndQuizId(user.getId(), quiz.getId());
            int score = getScore(questionOptionsId);
            quizMarks.put(title, score);
        }
        return quizMarks;
    }

    public void addAnswer(AnswerQuestionsRequest answerQuestionsRequest, User user) {
        List<QuestionOption> questionOptions = getQuestionOptions(answerQuestionsRequest);
        for (QuestionOption questionOption : questionOptions) {
            Answer answer = Answer.builder()
                    .user(user)
                    .question(questionOption.getQuestion())
                    .questionOption(questionOption)
                    .build();
            save(answer);
        }
    }

    public int getScore(List<Integer> questionOptionsId) {
        int result = 0;
        for (Integer integer : questionOptionsId) {

            QuestionOption questionOption = questionOptionRepository.getReferenceById(integer);
            int score = questionOption.getQuestion().getScore();
            QuestionType type = questionOption.getQuestion().getType();

            if (questionOption.getIsCorrect() & type.name().equals("SINGLE_SELECT")) {
                result += score;
            } else if (type.name().equals("MULTI_SELECT")) {
                List<Integer> correctQuestionOptionsId = questionOptionRepository.getCorrectQuestionOptionsId(questionOption.getQuestion().getId());
                if (questionOptionsId.containsAll(correctQuestionOptionsId)) {
                    result += score;
                }
            }
        }
        return result;
    }

    private List<QuestionOption> getQuestionOptions(AnswerQuestionsRequest answerQuestionsRequest) {
        List<QuestionOption> questionOptions = new ArrayList<>();
        for (Integer integer : answerQuestionsRequest.getQuestionOptionsId()) {
            questionOptions.add(questionOptionRepository.getReferenceById(integer));
        }
        return questionOptions;
    }


}
