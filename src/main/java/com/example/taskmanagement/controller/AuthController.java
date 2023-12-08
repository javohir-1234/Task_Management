package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.SignInDto;
import com.example.taskmanagement.entity.dto.UserRequestDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.exceptions.NotCorrectEmail;
import com.example.taskmanagement.exceptions.UserNotVerified;
import com.example.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;

    @Operation(
            description = "This endpoint is used for sign up",
            method = "Post method supported",
            security = @SecurityRequirement(name = "open", scopes = {"USER"})
    )
    @PostMapping("/sign-up")
    public UserResponseDto signUp(@RequestBody UserRequestDto userRequestDto) throws NotCorrectEmail {
        return userService.singUp(userRequestDto);
    }


    @PostMapping("/sign-in")
    public UserResponseDto signIn(@RequestBody SignInDto user) throws DataNotFound, UserNotVerified {
        return userService.singIn(user);
    }

    @Operation(
            description = "This endpoint is used for verifying user",
            method = "Post method supported",
            security = @SecurityRequirement(name = "open", scopes = {"USER"})
    )
    @PostMapping("/verify")
    public boolean verify(@RequestParam String email, @RequestParam int code) throws DataNotFound {
        return userService.verify(email, code);
    }

    @Operation(
            description = "This endpoint is used to get verification code",
            method = "Get method supported",
            security = @SecurityRequirement(name = "open", scopes = {"USER"})
    )
    @GetMapping("/get-verify-code")
    public void getVerifyCode(@RequestParam String email) throws DataNotFound {
        userService.sendVerificationCode(email);
    }

    @Operation(
            description = "This endpoint is used to get json web token",
            method = "Post method supported",
            security = @SecurityRequirement(name = "open", scopes = {"USER"})
    )
    @PostMapping("/generate-token")
    public String generateToken(@RequestBody UserRequestDto userRequestDto) throws DataNotFound {
        return userService.generateToken(userRequestDto);
    }

}
