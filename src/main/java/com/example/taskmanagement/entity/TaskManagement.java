package com.example.taskmanagement.entity;

import com.example.taskmanagement.entity.enumType.Priority;
import com.example.taskmanagement.entity.enumType.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task_management")
@Entity
public class TaskManagement extends BaseEntity{

    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private UUID creator;

    @OneToMany(mappedBy = "taskManagement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany()
    private List<User> executors;
}
