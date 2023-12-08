package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.BaseEntity;
import com.example.taskmanagement.entity.Comment;
import com.example.taskmanagement.entity.TaskManagement;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.dto.CommentRequestDto;
import com.example.taskmanagement.entity.dto.CommentResponseDto;
import com.example.taskmanagement.entity.dto.TaskManagementResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.repository.CommentRepository;
import com.example.taskmanagement.repository.TaskManagementRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService<
        Comment,
        UUID,
        CommentRepository,
        CommentResponseDto,
        CommentRequestDto>{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskManagementRepository taskManagementRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ModelMapper modelMapper() {
        return modelMapper;
    }

    @Override
    public CommentRepository repository() {
        return commentRepository;
    }

    public List<CommentResponseDto> findAllByTaskId(UUID id) throws DataNotFound {
        List<Comment> comments = commentRepository.findAllByTaskManagementId(id)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));

        return comments.stream()
                .filter(BaseEntity::isActive)
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Comment requestToEntity(CommentRequestDto request) throws DataNotFound {
        TaskManagement task = taskManagementRepository.findById(request.getTaskId())
                .orElseThrow(() -> new DataNotFound("Task Not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new DataNotFound("User not found"));
        Comment comment = modelMapper.map(request, Comment.class);
        if (task.isActive() && user.isActive()) {
            comment.setTaskManagement(task);
            comment.setUser(user);
        }else{
            throw new DataNotFound("Bad request");
        }
        return comment;
    }

    @Override
    public CommentResponseDto entityToResponse(Comment entity) {
        CommentResponseDto comment = modelMapper.map(entity, CommentResponseDto.class);
        comment.setUserId(entity.getUser().getId());
        return comment;
    }
}
