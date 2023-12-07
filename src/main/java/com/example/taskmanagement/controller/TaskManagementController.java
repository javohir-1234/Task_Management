package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.service.TaskManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-management")
public class TaskManagementController {


    private final TaskManagementService taskManagementService;
//
//    @PostMapping("/create-task")
//    public TaskManagementResponseDto
}
