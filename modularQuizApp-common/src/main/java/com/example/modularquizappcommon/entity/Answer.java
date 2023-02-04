package com.example.modularquizappcommon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "question_option_id")
    private QuestionOption questionOption;
    private LocalDateTime dateTime;

}
