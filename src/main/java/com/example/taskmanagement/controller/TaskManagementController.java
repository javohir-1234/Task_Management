package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.TaskManagementRequestDto;
import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.service.TaskManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task-management")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskManagementController {


    private final TaskManagementService taskManagementService;

    @PostMapping("/create-task")
    public TaskManagementResponseDto save(@RequestBody TaskManagementRequestDto requestDto) throws DataNotFound {
        return taskManagementService.create(requestDto);
    }

    @GetMapping("/get-tasks")
    public List<TaskManagementResponseDto> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
      return taskManagementService.getAll(page, size);
    }

    @DeleteMapping("/delete-task")
    public void deleteTask(@RequestParam UUID id) throws DataNotFound {
        System.out.println(id);
        taskManagementService.delete(id);
    }

    @PutMapping("/edit-task")
    public TaskManagementResponseDto update(
            @RequestParam UUID id,
            @RequestBody TaskManagementRequestDto requestDto
    ) throws DataNotFound {
        return taskManagementService.update(id, requestDto);
    }

    @GetMapping("/get-owner-task")
    public List<TaskManagementResponseDto> getTasksOfOwner(@RequestParam UUID id) throws DataNotFound {
        return taskManagementService.getTaskOfOwner(id);
    }
}
