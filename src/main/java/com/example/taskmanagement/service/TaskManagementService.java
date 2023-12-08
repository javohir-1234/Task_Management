package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.BaseEntity;
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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
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

    @Autowired
    private CommentService commentService;

    @Override
    public TaskManagementResponseDto create(TaskManagementRequestDto request) throws DataNotFound {
        User user = userRepository.findById(request.getCreator())
                .orElseThrow(() -> new DataNotFound("User not found"));
        if (user.isVerify()) {
            return super.create(request);
        }

        throw new RuntimeException("User not verified");
    }

    public List<TaskManagementResponseDto> getTaskOfOwner(UUID id) throws DataNotFound {
        List<TaskManagement> task = taskManagementRepository.findByCreator(id)
                .orElseThrow(() -> new DataNotFound("Task not found"));

       return task.stream()
                .filter(BaseEntity::isActive)
                .map(this::entityToResponse)
                .collect(Collectors.toList());
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
    public TaskManagement requestToEntity(TaskManagementRequestDto request) throws DataNotFound {
        TaskManagement taskManagement = modelMapper.map(request, TaskManagement.class);
        User user = userRepository.findById(request.getCreator())
                .orElseThrow(() -> new DataNotFound("User Not Found"));
        if (user.isActive() && taskManagement.isActive()) {
            taskManagement.setComments(
                    request.getComments()
                            .stream()
                            .map(e -> new Comment(e.getComment(), user, taskManagement))
                            .collect(Collectors.toList())
            );
            return taskManagement;
        }
        throw new DataNotFound("Data Not Found");
    }

    @Override
    public TaskManagementResponseDto entityToResponse(TaskManagement entity) {
        TaskManagementResponseDto taskManagement = modelMapper.map(entity, TaskManagementResponseDto.class);
        taskManagement.setComments(
                entity.getComments()
                        .stream()
                        .map(e-> new CommentResponseDto(e.getComment(), e.getUser().getId()))
                        .collect(Collectors.toList())
        );
        return taskManagement;
    }
}
