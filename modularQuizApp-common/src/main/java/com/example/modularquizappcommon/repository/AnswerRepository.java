package com.example.modularquizappcommon.repository;

import com.example.modularquizappcommon.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Modifying
    @Query(value = "DELETE answer FROM answer LEFT JOIN question ON answer.question_id = question.id WHERE question.quiz_id = ?", nativeQuery = true)
    void deleteAllByQuizId(int quizId);

    @Query(value = "SELECT question_option_id FROM answer \n" +
            "INNER JOIN question ON answer.question_id = question.id WHERE user_id = ? AND quiz_id = ?", nativeQuery = true)
    List<Integer> getAllQuestionOptionsIdByUserIdAndQuizId(int userId, int quizId);


}
