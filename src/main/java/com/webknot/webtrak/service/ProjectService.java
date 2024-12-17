package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.CreateProjectDTO;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateService class.
 * It will have business logic.
 */
@Service
@Slf4j
public class ProjectService {

  final ProjectRepository projectRepository;
  final UserService userService;

  public ProjectService(ProjectRepository projectRepository, UserService userService) {
    this.projectRepository = projectRepository;
    this.userService = userService;
  }

  public Project createProject(CreateProjectDTO createProjectDTO) {

    Project extProject = projectRepository.findByProjectCode(createProjectDTO.getProjectCode());
    if (extProject != null) {
      throw new BadRequestException("Project already exists");
    }

    User user = userService.getUserByEmail(createProjectDTO.getManagerEmail());
    if (user == null) {
      throw new BadRequestException("Manager not found");
    }

    Project project = new Project();
    project.setProjectCode(createProjectDTO.getProjectCode());
    project.setProjectName(createProjectDTO.getProjectName());
    project.setManager(user);

    return projectRepository.save(project);

  }


  public Project getProjectByCode(String code) {
    return projectRepository.findByProjectCode(code);
  }

}
