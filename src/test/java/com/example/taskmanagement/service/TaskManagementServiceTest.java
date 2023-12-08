package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.TaskManagement;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.dto.TaskManagementRequestDto;
import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.entity.enumType.Priority;
import com.example.taskmanagement.entity.enumType.Status;
import com.example.taskmanagement.entity.enumType.UserRole;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.repository.TaskManagementRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagementServiceTest {



    @Mock
    private TaskManagementRepository taskManagementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentService commentService;

    @Mock
    private ModelMapper modelMapper;
    private TaskManagementService taskManagementService;
    private TaskManagement taskManagement;
    private TaskManagementRequestDto taskManagementRequestDto;
    private TaskManagementResponseDto taskManagementResponseDto;
    private User user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Set<UserRole> userRoles = new TreeSet<>();
        userRoles.add(UserRole.USER);
        user = new User("test", "test", "test@gmail.com", "3333", userRoles, true, 4545 );
        user.setId(UUID.randomUUID());
        taskManagementRequestDto = new TaskManagementRequestDto("test title", "test description", Status.AWAITING, Priority.HIGH, UUID.randomUUID(), List.of(), List.of());
        taskManagement = new TaskManagement("test title", "test description", Status.AWAITING, Priority.HIGH, UUID.randomUUID(), List.of(), List.of());
        taskManagementResponseDto = new TaskManagementResponseDto("test title", "test description", Status.AWAITING, Priority.HIGH, UUID.randomUUID(), List.of(), List.of());
        taskManagementService = new TaskManagementService(modelMapper, taskManagementRepository, userRepository, commentService);
    }

    @Test
    void create() throws DataNotFound {
        when(userRepository.findById(taskManagementRequestDto.getCreator()))
                .thenReturn(Optional.of(user));
        when(modelMapper.map(taskManagement, TaskManagementResponseDto.class))
                .thenReturn(taskManagementResponseDto);
        when(modelMapper.map(taskManagementRequestDto, TaskManagement.class))
                .thenReturn(taskManagement);

        TaskManagementResponseDto taskManagementResponseDto = taskManagementService.create(taskManagementRequestDto);
        assertNotNull(taskManagementResponseDto);
    }


}