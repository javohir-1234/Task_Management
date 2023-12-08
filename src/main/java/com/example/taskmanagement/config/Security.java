package com.example.taskmanagement.config;

import com.example.taskmanagement.service.jwt.AuthenticationService;
import com.example.taskmanagement.service.jwt.JWTFilter;
import com.example.taskmanagement.service.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

    private static final String[] WHITE_LIST = {
            "/auth/sign-up",
            "/auth/sign-in",
            "/auth/verify",
            "/auth/generate-token",
            "/auth/get-verify-code",
//            "/task-management/create-task",
//            "/task-management/delete-task",
//            "/task-management/edit-task",
//            "/task-management/get-tasks",
    };
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;


    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestConfigurer -> {
                    requestConfigurer.requestMatchers(WHITE_LIST).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JWTFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .build();
    }
}
