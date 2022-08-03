package com.example.projectmanagement.service;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.validate.response.ApiResponse;
import com.example.projectmanagement.validate.response.OutputProjectDTO;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ProjectService {
    ResponseEntity<ApiResponse> addProject(ProjectEntity project);
    ResponseEntity<OutputProjectDTO> getProject(Long projectId, String auth);
    ResponseEntity<Set<OutputProjectDTO>> getProjects(Long departmentId, String auth);
    ResponseEntity<ApiResponse> deleteProject(Long projectId);
}
