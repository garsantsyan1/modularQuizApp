package com.example.modularquizappcommon.security;

import com.example.modularquizappcommon.entity.User;
import com.example.modularquizappcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException("username" + username + " not found");
        }
        return new CurrentUser(byEmail.get());
    }
}

