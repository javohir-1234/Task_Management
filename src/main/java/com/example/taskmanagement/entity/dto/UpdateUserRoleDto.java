package com.example.taskmanagement.entity.dto;

import com.example.taskmanagement.entity.enumType.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRoleDto {

    private UUID userId;
    private Set<UserRole> roles;
}
