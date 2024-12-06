package com.webknot.webtrak.repository;

import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.TimeLog;
import com.webknot.webtrak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateRepository class.
 * It will have methods to interact with data source.
 */
@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

    List<TimeLog> getTimeLogByDateAndUser(Date date, User user);

    List<TimeLog> getTimeLogByDateAndUserAndProject(Date date, User employee, Project project);

    List<TimeLog> getTimeLogByUserAndProject(User user, Project project);
}
