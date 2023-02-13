package com.example.modularquizappcommon.repository;

import com.example.modularquizappcommon.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuiz_Id(int id);

    List<Question> getQuestionsByQuizId(int quiz_id);

}
