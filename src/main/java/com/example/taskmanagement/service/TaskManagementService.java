package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Comment;
import com.example.taskmanagement.entity.TaskManagement;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.dto.CommentResponseDto;
import com.example.taskmanagement.entity.dto.TaskManagementRequestDto;
import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.repository.TaskManagementRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskManagementResponseDto create(TaskManagementRequestDto request) throws DataNotFound {
        User user = userRepository.findById(request.getCreator())
                .orElseThrow(() -> new DataNotFound("User not found"));
        if (user.isVerify()) {
            return super.create(request);
        }

        throw new RuntimeException("User not verified");
    }

    public TaskManagementResponseDto getTaskOfOwner(UUID id) throws DataNotFound {
        TaskManagement task = taskManagementRepository.findByCreator(id)
                .orElseThrow(() -> new DataNotFound("Task not found"));
        if (task.isActive()){
            return entityToResponse(task);
        }

        throw new DataNotFound("This task not exist");
    }



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
        TaskManagement taskManagement = modelMapper.map(request, TaskManagement.class);
        taskManagement.setComments(
                request.getComments()
                        .stream()
                        .map(e-> new Comment(e.getComment(), e.getUser(), taskManagement))
                        .collect(Collectors.toList())
        );
        return taskManagement;
    }

    @Override
    public TaskManagementResponseDto entityToResponse(TaskManagement entity) {
        TaskManagementResponseDto taskManagement = modelMapper.map(entity, TaskManagementResponseDto.class);
        taskManagement.setComments(
                entity.getComments()
                        .stream()
                        .map(e-> new CommentResponseDto(e.getComment(), e.getUserId()))
                        .collect(Collectors.toList())
        );
        return taskManagement;
    }
}
