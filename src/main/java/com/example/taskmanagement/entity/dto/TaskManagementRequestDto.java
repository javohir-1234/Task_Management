package com.example.taskmanagement.entity.dto;

import com.example.taskmanagement.entity.enumType.Priority;
import com.example.taskmanagement.entity.enumType.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskManagementRequestDto {

    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UUID userId;
}
