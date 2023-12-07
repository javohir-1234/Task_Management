package com.example.taskmanagement.validation;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exceptions.NotCorrectEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class Validation {

    public boolean checkingEmail(User user) throws NotCorrectEmail {
        return Pattern.matches("^[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*@[a-zA-Z]+\\.[a-zA-Z]{2,}$", user.getEmail());
    }
}
