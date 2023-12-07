package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.SignInDto;
import com.example.taskmanagement.entity.dto.UserRequestDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.exceptions.NotCorrectEmail;
import com.example.taskmanagement.exceptions.UserNotVerified;
import com.example.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;


    @PostMapping("/sing-up")
    public UserResponseDto signUp(@RequestBody UserRequestDto userRequestDto) throws NotCorrectEmail {
        return userService.singUp(userRequestDto);
    }


    @PostMapping("/sing-in")
    public UserResponseDto signIn(@RequestBody SignInDto user) throws DataNotFound, UserNotVerified {
        return userService.singIn(user);
    }

    @PostMapping("/verify")
    public boolean verify(@RequestParam String email, @RequestParam int code) throws DataNotFound {
        return userService.verify(email, code);
    }

    @GetMapping("/get-verify-code")
    public void getVerifyCode(@RequestParam String email) throws DataNotFound {
        userService.sendVerificationCode(email);
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody UserRequestDto userRequestDto) throws DataNotFound {
        return userService.generateToken(userRequestDto);
    }

}
