package com.example.projectmanagement.controller;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.service.ProjectService;
import com.example.projectmanagement.validate.response.ApiResponse;
import com.example.projectmanagement.validate.response.OutputProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/project-management/api/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProject(@RequestBody ProjectEntity project) {
        return projectService.addProject(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputProjectDTO> getProject(@PathVariable(name = "id") Long projectId,
                                                       @RequestHeader(value = "Authorization") String auth) {
        return projectService.getProject(projectId, auth);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Set<OutputProjectDTO>> getProjects(@PathVariable(name = "id") Long departmentId,
                                                             @RequestHeader(value = "Authorization") String auth) {
        return projectService.getProjects(departmentId, auth);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable(name = "id") Long projectId) {
        return projectService.deleteProject(projectId);
    }
}
