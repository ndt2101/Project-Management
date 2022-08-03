package com.example.projectmanagement.service.impl;

import com.example.projectmanagement.entity.ProjectEntity;
import com.example.projectmanagement.repository.ProjectRepository;
import com.example.projectmanagement.service.ProjectService;
import com.example.projectmanagement.validate.response.ApiResponse;
import com.example.projectmanagement.validate.response.DepartmentDTO;
import com.example.projectmanagement.validate.response.OutputProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponse> addProject(ProjectEntity project) {
        projectRepository.save(project);
        return ResponseEntity.ok(new ApiResponse(true, "Add project successfully!"));
    }

    @Override
    public ResponseEntity<OutputProjectDTO> getProject(Long projectId, String auth) {
        Optional<ProjectEntity> project = projectRepository.findById(projectId);
        OutputProjectDTO result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        if (project.isPresent()) {
            DepartmentDTO departmentDTO = restTemplate
                    .exchange("http://localhost:8080/api/department/"
                                    + project.get().getDepartmentId(),
                            HttpMethod.GET,
                            entity,
                            DepartmentDTO.class).getBody(); // TODO: chua xu ly rollback
            result = modelMapper.map(project.get(), OutputProjectDTO.class);
            result.setDepartment(departmentDTO);
        }
        return ResponseEntity
                .status(project.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(result);
    }

    @Override
    public ResponseEntity<Set<OutputProjectDTO>> getProjects(Long departmentId, String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        Set<ProjectEntity> projects = projectRepository.findAllByDepartmentId(departmentId);
        Set<OutputProjectDTO> result = projects.parallelStream().map(projectEntity -> {
            OutputProjectDTO outputProjectDTO = modelMapper.map(projectEntity, OutputProjectDTO.class);
            DepartmentDTO departmentDTO = restTemplate
                    .exchange("http://localhost:8080/api/department/"
                                    + projectEntity.getDepartmentId(),
                            HttpMethod.GET,
                            entity,
                            DepartmentDTO.class).getBody(); // TODO: chua xu ly rollback
            outputProjectDTO.setDepartment(departmentDTO);
            return outputProjectDTO;
        }).collect(Collectors.toSet());
        return ResponseEntity.status(projects.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(result);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
        return ResponseEntity.ok(new ApiResponse(true, "Add project successfully!"));
    }
}
