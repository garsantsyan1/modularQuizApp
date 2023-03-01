package com.example.modularquizappcommon.service;


import com.example.modularquizappcommon.entity.User;
import com.example.modularquizappcommon.entity.UserType;
import com.example.modularquizappcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAllStudents() {
        return userRepository.findAllByType(UserType.STUDENT);
    }

    public User getUserById(int id) {
        return userRepository.getReferenceById(id);
    }


    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
