package com.webknot.webtrak.service;

import com.webknot.webtrak.dto.CreateProjectDTO;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
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

    Project extProject = projectRepository.findByProjectName(createProjectDTO.getProjectName());
    if (extProject != null) {
      return null;
    }

    User user = userService.getUserByEmail(createProjectDTO.getManagerEmail());
    if (user == null) {
      return null;
    }

    Project project = new Project();
    project.setProjectName(createProjectDTO.getProjectName());
    project.setManager(user);

    return projectRepository.save(project);

  }


  public Optional<Project> getProjectById(Long id) {
    return projectRepository.findById(id);
  }

}