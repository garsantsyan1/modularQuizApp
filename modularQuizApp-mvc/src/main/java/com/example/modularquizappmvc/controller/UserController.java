package com.example.modularquizappmvc.controller;


import com.example.modularquizappcommon.entity.User;
import com.example.modularquizappmvc.security.CurrentUser;
import com.example.modularquizappmvc.service.AnswerService;
import com.example.modularquizappmvc.service.QuizService;
import com.example.modularquizappmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final QuizService quizService;

    private final AnswerService answerService;

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user) {
        userService.create(user);
        return "loginPage";
    }


    @GetMapping("/teach/page")
    public String teacherPage(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("quizzes", quizService.findAll());
        map.addAttribute("user", currentUser.getUser());
        return "teachDash";
    }

    @GetMapping("/student/page")
    public String studentPage(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("quizzes", quizService.findAll());
        map.addAttribute("user", currentUser.getUser());
        return "studentDash";
    }


    @GetMapping("/all/students/")
    public String allStudents(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("students", userService.getAllStudents());
        map.addAttribute("user", currentUser.getUser());
        return "students";
    }


    @GetMapping("/studentQuizzes/{id}")
    public String studentQuizzes(@PathVariable("id") int id, ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userService.getUserById(id);
        Map<String, String> quizzesAndMarks = answerService.getQuizzesAndMarks(user);
        map.addAttribute("user", currentUser.getUser());
        map.addAttribute("quizMarks", quizzesAndMarks);
        return "studentQuizzesResults";

    }

}
