package com.example.modularquizappcommon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private String title;
    private int score;
    @ManyToOne
    private Quiz quiz;
    @OneToMany(mappedBy = "question")
    private List<QuestionOption> questionOptions;
    @OneToMany(mappedBy = "question")
    private List <Answer> answers;

}

