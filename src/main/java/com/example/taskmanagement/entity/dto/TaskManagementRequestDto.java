package com.example.taskmanagement.entity.dto;

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
    private UUID userId;
}
