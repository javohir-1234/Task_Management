package com.example.taskmanagement.service.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiry}")
    private String expiry;

//    public String generateToken()

}
