package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.CreateProjectDTO;
import com.webknot.webtrak.dto.GenericResponseDTO;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.ProjectService;
import com.webknot.webtrak.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateServiceController class.
 * It will have all API end points definition.
 */
@RestController
public class ProjectServiceController extends BaseController {

    private final ProjectService projectService;

    @Value("${webtrak.adminPassword}")
    private String adminPassword;

    public ProjectServiceController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("project")
    public ResponseEntity<GenericResponseDTO> createProject(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody CreateProjectDTO createProjectDTO) {

        try {
            Utils.verifyPassword(authorization, adminPassword);
            return ResponseEntity.ok().body(new GenericResponseDTO("success", projectService.createProject(createProjectDTO)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }

    }

    @GetMapping(value = "project")
    public ResponseEntity<GenericResponseDTO> getProject(@RequestParam(required = true) String projectCode) {

        try {
            return ResponseEntity.ok().body(new GenericResponseDTO("success", projectService.getProjectByCode(projectCode)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }
    }

}
