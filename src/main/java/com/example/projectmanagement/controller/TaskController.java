package com.example.projectmanagement.controller;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.entity.TaskEntity;
import com.example.projectmanagement.service.TaskService;
import com.example.projectmanagement.validate.payload.InputTaskDTO;
import com.example.projectmanagement.validate.response.ApiResponse;
import com.example.projectmanagement.validate.response.OutputTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/project-management/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createTask(@RequestBody InputTaskDTO task) {
        return taskService.addTask(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputTaskDTO> getTask(@PathVariable(name = "id") Long taskId,
                                                 @RequestHeader(value = "Authorization") String auth) {
        return taskService.getTask(taskId, auth);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Set<OutputTaskDTO>> getTasks(@PathVariable(name = "id") Long projectId,
                                                    @RequestHeader(value = "Authorization") String auth) {
        return taskService.getTasks(projectId, auth);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable(name = "id") Long taskId) {
        return taskService.deleteTask(taskId);
    }

}
