package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.TaskManagement;
import com.example.taskmanagement.entity.dto.TaskManagementRequestDto;
import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.repository.TaskManagementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskManagementService extends BaseService<
        TaskManagement,
        UUID,
        TaskManagementRepository,
        TaskManagementResponseDto,
        TaskManagementRequestDto> {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskManagementRepository taskManagementRepository;




    @Override
    public ModelMapper modelMapper() {
        return modelMapper;
    }

    @Override
    public TaskManagementRepository repository() {
        return taskManagementRepository;
    }

    @Override
    public TaskManagement requestToEntity(TaskManagementRequestDto request) {
        return modelMapper.map(request, TaskManagement.class);
    }

    @Override
    public TaskManagementResponseDto entityToResponse(TaskManagement entity) {
        return modelMapper.map(entity, TaskManagementResponseDto.class);
    }
}
