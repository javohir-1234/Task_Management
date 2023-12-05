package com.example.taskmanagement.entity.dto;

import com.example.taskmanagement.entity.enumType.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String name;
    private String familyName;
    private String email;
    private Set<UserRole> roles;
}
