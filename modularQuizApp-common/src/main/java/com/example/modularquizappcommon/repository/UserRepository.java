package com.example.modularquizappcommon.repository;

import com.example.modularquizappcommon.entity.User;
import com.example.modularquizappcommon.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    List<User> findAllByType(UserType userType);
}
