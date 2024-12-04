package com.webknot.webtrak.repository;

import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateRepository class.
 * It will have methods to interact with data source.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectName(String projectName);
}
