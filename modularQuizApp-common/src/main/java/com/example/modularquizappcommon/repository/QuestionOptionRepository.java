package com.example.modularquizappcommon.repository;

import com.example.modularquizappcommon.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Integer> {

    @Query(value = "SELECT id FROM question_option WHERE question_id = ? AND is_correct = TRUE", nativeQuery = true)
    List<Integer> getCorrectQuestionOptionsId(int questionId);

}
