package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.dto.UpdateUserRoleDto;
import com.example.taskmanagement.entity.dto.UserRequestDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/change-role")
    public UserResponseDto  changeRole(@RequestBody UpdateUserRoleDto userRoleDto) throws DataNotFound {
        return userService.updateUserRole(userRoleDto);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-all-user")
    public List<UserResponseDto> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return userService.getAll(page, size);
    }


}
