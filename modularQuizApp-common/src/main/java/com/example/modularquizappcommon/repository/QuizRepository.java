package com.example.modularquizappcommon.repository;

import com.example.modularquizappcommon.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {


}
