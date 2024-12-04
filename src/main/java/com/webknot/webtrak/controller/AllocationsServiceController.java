package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.CreateProjectDTO;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateServiceController class.
 * It will have all API end points definition.
 */
@RestController
public class AllocationsServiceController extends BaseController {

    private final ProjectService projectService;

    public AllocationsServiceController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("project")
    public ResponseEntity<?> createProject(@RequestBody CreateProjectDTO createProjectDTO) {
        Project project = projectService.createProject(createProjectDTO);

        if (project != null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "project")
    public Project getProject(@RequestParam(required = true) Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.orElse(null);
    }

}
