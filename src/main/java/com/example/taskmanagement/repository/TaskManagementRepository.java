package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.TaskManagement;
import com.example.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskManagementRepository extends JpaRepository<TaskManagement, UUID> {

    Optional<List<TaskManagement>> findByCreator(UUID uuid);
}
