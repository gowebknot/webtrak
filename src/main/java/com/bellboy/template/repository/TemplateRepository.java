package com.bellboy.template.repository;

import com.bellboy.template.entity.EntityClassName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateRepository class.
 * It will have methods to interact with data source.
 */
@Repository
public interface TemplateRepository extends JpaRepository<EntityClassName, Long> {

  //TODO - Write data repository logic here.

}
