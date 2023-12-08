package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.dto.UserRequestDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.entity.enumType.UserRole;
import com.example.taskmanagement.exceptions.NotCorrectEmail;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.jwt.JWTService;
import com.example.taskmanagement.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;
    @Mock
    private NotificationService notificationService;

    @Mock
    private Validation validation;

    @Mock
    private Random random;
    private UserService userService;


    private User user;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Set<UserRole> userRoles = new TreeSet<>();
        userRoles.add(UserRole.USER);
        user = new User("test", "test", "something123@gmail.com", "test", userRoles, true, 4545);
        userResponseDto = new UserResponseDto("test", "test", "test@gmail.com", userRoles);
        userRequestDto = new UserRequestDto("test", "test", "test@gmail.com", "test");
        userService = new UserService(userRepository, modelMapper, passwordEncoder, jwtService, notificationService,validation, random);
    }
    @Test
    void singUp() throws NotCorrectEmail {
        Mockito.when(modelMapper.map(userRequestDto, User.class))
                .thenReturn(user);
        Mockito.when(modelMapper.map(user, UserResponseDto.class))
                .thenReturn(userResponseDto);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Mockito.when(validation.checkingEmail(user))
                .thenReturn(true);

        UserResponseDto signedUp = userService.singUp(userRequestDto);
        assertNotNull(signedUp);
    }
}