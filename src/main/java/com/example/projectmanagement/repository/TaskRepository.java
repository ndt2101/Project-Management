package com.example.projectmanagement.repository;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findById(Long id);
    Set<TaskEntity> findAllByProject(ProjectEntity project);
}
