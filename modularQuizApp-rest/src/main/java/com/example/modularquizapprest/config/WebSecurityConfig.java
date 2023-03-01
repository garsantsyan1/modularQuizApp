package com.example.modularquizapprest.config;


import com.example.modularquizappcommon.security.UserDetailsImpl;
import com.example.modularquizapprest.security.JWTAuthenticationTokenFilter;
import com.example.modularquizapprest.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/auth").permitAll()
                        .requestMatchers("/user/reg").permitAll()
                        .requestMatchers("/all/students/").hasAnyAuthority("TEACHER")
                        .requestMatchers("/quiz/add").hasAnyAuthority("TEACHER")
                        .requestMatchers("/deleteQuiz/{id}").hasAnyAuthority("TEACHER")
                        .requestMatchers("/studentQuizzes/{id}").hasAnyAuthority("TEACHER")
                        .requestMatchers("/answer").hasAnyAuthority("STUDENT")
                        .requestMatchers("/question/add").hasAnyAuthority("TEACHER")
                        .requestMatchers("/deleteQuestion/{id}").hasAnyAuthority("TEACHER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl().and()
                .and().build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsImpl userDetails)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public JWTAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }


}