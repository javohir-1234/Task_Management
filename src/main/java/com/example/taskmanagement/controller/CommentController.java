package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.CommentRequestDto;
import com.example.taskmanagement.entity.dto.CommentResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @Operation(
            description = "This endpoint is used for create comment",
            method = "Post method supported",
            security = @SecurityRequirement(name = "open", scopes = {"USER"})
    )
    @PostMapping("/create-comment")
    public CommentResponseDto save(@RequestBody CommentRequestDto requestDto) throws DataNotFound {
        return commentService.create(requestDto);
    }

    @GetMapping("/get-comment")
    public CommentResponseDto get(@RequestParam UUID id) throws DataNotFound {
        return commentService.getById(id);
    }

    @DeleteMapping("/delete-comment")
    public void delete(@RequestParam UUID id) throws DataNotFound {
        commentService.delete(id);
    }

    @PutMapping("/edit-comment")
    public CommentResponseDto update(@RequestParam UUID id, @RequestBody CommentRequestDto requestDto) throws DataNotFound {
        return commentService.update(id, requestDto);
    }

    @GetMapping("/get-by-task-id")
    public List<CommentResponseDto> getByTaskId(@RequestParam UUID id) throws DataNotFound {
        return commentService.findAllByTaskId(id);
    }


    @GetMapping("/get-all-comment")
    public List<CommentResponseDto> getAllComment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return commentService.getAll(page, size);
    }
}
