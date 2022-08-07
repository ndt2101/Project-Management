package com.example.projectmanagement.service.impl;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.entity.TaskEntity;
import com.example.projectmanagement.repository.ProjectRepository;
import com.example.projectmanagement.repository.TaskRepository;
import com.example.projectmanagement.service.TaskService;
import com.example.projectmanagement.validate.payload.InputTaskDTO;
import com.example.projectmanagement.validate.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public ResponseEntity<ApiResponse> addTask(InputTaskDTO task) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(task.getProjectId());
        if (projectEntity.isPresent()) {
            TaskEntity taskEntity = modelMapper.map(task, TaskEntity.class);
            taskEntity.setProject(projectEntity.get());
            taskRepository.save(taskEntity);
            return ResponseEntity.ok(new ApiResponse(true, "Add task successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Project not found"));
        }
    }

    @Override
    public ResponseEntity<OutputTaskDTO> getTask(Long taskId, String auth) {
        Optional<TaskEntity> task = taskRepository.findById(taskId);
        OutputTaskDTO result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        if (task.isPresent()) {
            EmployeeDTO employeeDTO = restTemplate
                    .exchange("http://STAFF-SERVICE:8080/staff-management/api/employee/"
                                    + task.get().getEmployeeId(),
                            HttpMethod.GET,
                            entity,
                            EmployeeDTO.class).getBody(); // TODO: chua xu ly rollback
            result = modelMapper.map(task.get(), OutputTaskDTO.class);
            result.setEmployee(employeeDTO);
            OutputProjectDTO projectDTO = modelMapper.map(task.get().getProject(), OutputProjectDTO.class);
            result.setProject(projectDTO);
        }
        return ResponseEntity
                .status(task.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(result);
    }

    @Override
    public ResponseEntity<Set<OutputTaskDTO>> getTasks(Long projectId, String auth) {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", auth);
            HttpEntity<Object> entity = new HttpEntity<>(null, headers);
            Set<TaskEntity> tasks = taskRepository.findAllByProject(optionalProject.get());
            Set<OutputTaskDTO> result = tasks.parallelStream().map(taskEntity -> {
                OutputTaskDTO outputTaskDTO = modelMapper.map(taskEntity, OutputTaskDTO.class);
                EmployeeDTO employeeDTO = restTemplate
                        .exchange("http://STAFF-SERVICE:8080/staff-management/api/employee/"
                                        + taskEntity.getEmployeeId(),
                                HttpMethod.GET,
                                entity,
                                EmployeeDTO.class).getBody(); // TODO: chua xu ly rollback
                outputTaskDTO.setEmployee(employeeDTO);
                OutputProjectDTO projectDTO = modelMapper.map(taskEntity.getProject(), OutputProjectDTO.class);
                outputTaskDTO.setProject(projectDTO);
                return outputTaskDTO;
            }).collect(Collectors.toSet());
            return ResponseEntity.status(tasks.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashSet<>());

        }

    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        return ResponseEntity.ok(new ApiResponse(true, "Add task successfully"));
    }
}
