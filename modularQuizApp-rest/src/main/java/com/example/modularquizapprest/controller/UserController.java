package com.example.modularquizapprest.controller;


import com.example.modularquizappcommon.entity.User;
import com.example.modularquizappcommon.service.AnswerService;
import com.example.modularquizappcommon.service.UserService;
import com.example.modularquizapprest.dto.SaveUserRequest;
import com.example.modularquizapprest.dto.UserLoginRequest;
import com.example.modularquizapprest.dto.UserLoginResponse;
import com.example.modularquizapprest.dto.UserResponseDto;
import com.example.modularquizapprest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AnswerService answerService;

    @PostMapping("/user/auth")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getEmail() != null && !userLoginRequest.getEmail().equals("")) {
            Optional<User> byEmail = userService.findUserByEmail(userLoginRequest.getEmail());
            if (byEmail.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(), byEmail.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(new UserLoginResponse(jwtTokenUtil.generateToken(byEmail.get().getEmail())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping("/user/reg")
    public ResponseEntity<UserResponseDto> userRegistration(@RequestBody SaveUserRequest saveUserRequest) {
        User user = modelMapper.map(saveUserRequest, User.class);
        if (userService.findUserByEmail(saveUserRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        userService.create(user);
        return ResponseEntity.ok(modelMapper.map(user, UserResponseDto.class));
    }

    @GetMapping("/all/students/")
    public List<UserResponseDto> allStudents() {
        List<UserResponseDto> students = new ArrayList<>();
        for (User student : userService.getAllStudents()) {
            students.add(modelMapper.map(student, UserResponseDto.class));
        }
        return students;
    }

    @GetMapping("/studentQuizzes/{id}")
    public Map<String, String> studentQuizzes(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return answerService.getQuizzesAndMarks(user);
    }

}

