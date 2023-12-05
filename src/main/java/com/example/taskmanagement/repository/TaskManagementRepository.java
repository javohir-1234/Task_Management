package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.TaskManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskManagementRepository extends JpaRepository<TaskManagement, UUID> {

}
