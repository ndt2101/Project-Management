package com.example.projectmanagement.service;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.entity.TaskEntity;
import com.example.projectmanagement.validate.payload.InputTaskDTO;
import com.example.projectmanagement.validate.response.ApiResponse;
import com.example.projectmanagement.validate.response.OutputTaskDTO;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface TaskService {
    ResponseEntity<ApiResponse> addTask(InputTaskDTO task);
    ResponseEntity<OutputTaskDTO> getTask(Long taskId, String auth);
    ResponseEntity<Set<OutputTaskDTO>> getTasks(Long projectId, String auth);
    ResponseEntity<ApiResponse> deleteTask(Long taskId);
}
