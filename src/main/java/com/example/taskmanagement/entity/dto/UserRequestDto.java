package com.example.taskmanagement.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {


    private String name;
    private String familyName;
    private String email;
    private String password;

}
