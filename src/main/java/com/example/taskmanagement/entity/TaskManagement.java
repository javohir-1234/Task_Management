package com.example.taskmanagement.entity;

import com.example.taskmanagement.entity.enumType.Priority;
import com.example.taskmanagement.entity.enumType.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Status status;
    private Priority priority;
    @OneToOne
    private User user;
}
