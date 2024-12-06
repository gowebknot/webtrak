package com.webknot.webtrak.service;

import com.sun.jdi.request.InvalidRequestStateException;
import com.webknot.webtrak.dto.AddAllocationDTO;
import com.webknot.webtrak.dto.AddTimeLogDTO;
import com.webknot.webtrak.dto.UpdateTimeLogDTO;
import com.webknot.webtrak.dto.UpdateTimeLogEntryDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.TimeLog;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.repository.AllocationsRepository;
import com.webknot.webtrak.repository.TimeLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateService class.
 * It will have business logic.
 */
@Service
@Slf4j
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final ProjectService projectService;
    private final AllocationsRepository allocationsRepository;

    private static final String approved = "APPROVED";
    private static final String submitted = "SUBMITTED";

    private static final String rejected = "REJECTED";

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public TimeLogService(TimeLogRepository timeLogRepository, ProjectService projectService, AllocationsRepository allocationsRepository) {
        this.timeLogRepository = timeLogRepository;
        this.projectService = projectService;
        this.allocationsRepository = allocationsRepository;
    }

    public TimeLog addTimeLog(AddTimeLogDTO addTimeLogDTO, User user) throws ParseException {

        Project project = projectService.getProjectByCode(addTimeLogDTO.getProjectCode());

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist");
        }

        TimeLog timeLog = new TimeLog();
        timeLog.setUser(user);
        timeLog.setProject(project);
        timeLog.setLoggedHours(addTimeLogDTO.getLoggedHours());
        timeLog.setDescription(addTimeLogDTO.getDescription());

        Date date = sdf.parse(addTimeLogDTO.getDate());
        timeLog.setDate(new java.sql.Date(date.getTime()));

        timeLog.setStatus(submitted);

        return timeLogRepository.save(timeLog);

    }

    public TimeLog updateTimeLogStatus(Long timeLogId, String status, User approver) {
        Optional<TimeLog> timeLog = timeLogRepository.findById(timeLogId);

        if (timeLog.isEmpty()) {
            throw new BadRequestException("TimeLog does not exist");
        }

        if (!timeLog.get().getProject().getManager().getEmail().equalsIgnoreCase(approver.getEmail())) {
            throw new BadRequestException("Needs to be approved by a manager");
        }

        TimeLog extTimelog = timeLog.get();
        extTimelog.setStatus(status);

        return timeLogRepository.save(extTimelog);
    }

    public void updateTimeLogStatusByDateAndUserAndProject(UpdateTimeLogDTO updateTimeLogDTO, User employee,
                                                           User approver) throws ParseException {

        Project project = projectService.getProjectByCode(updateTimeLogDTO.getProjectCode());

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(employee.getId(),
                project.getId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist");
        }

        if (!project.getManager().getEmail().equalsIgnoreCase(approver.getEmail())) {
            throw new BadRequestException("Needs to be approved by a manager");
        }

        Date date = sdf.parse(updateTimeLogDTO.getDate());

        List<TimeLog> timeLogs = timeLogRepository
                .getTimeLogByDateAndUserAndProject(new java.sql.Date(date.getTime()), employee, project);

        for (TimeLog timeLog : timeLogs) {
            if (updateTimeLogDTO.getStatus().equalsIgnoreCase(approved)) {
                timeLog.setStatus(approved);
            } else if (updateTimeLogDTO.getStatus().equalsIgnoreCase(rejected)) {
                timeLog.setStatus(rejected);
            } else {
                continue;
            }
            timeLogRepository.save(timeLog);
        }
    }

    public List<TimeLog> getTimeLogsByDateAndUserAndProject(String date, User user, String projectCode) throws ParseException {

        Date parseDate = sdf.parse(date);

        Project project = projectService.getProjectByCode(projectCode);

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist");
        }

        return timeLogRepository.getTimeLogByDateAndUserAndProject(new java.sql.Date(parseDate.getTime()),
                user, project);
    }

    public List<TimeLog> getTimeLogsByUserAndProject(User user, String projectCode) {

        Project project = projectService.getProjectByCode(projectCode);

        if (project == null) {
            throw new BadRequestException("Project does not exist");
        }

        Allocation extAllocation = allocationsRepository.findByUserIdAndProjectId(user.getId(),
                project.getId());

        if (extAllocation == null) {
            throw new BadRequestException("Allocation does not exist");
        }

        return timeLogRepository.getTimeLogByUserAndProject(user, project);
    }

    public TimeLog getTimeLogById(Long id) {
        Optional<TimeLog> timeLog = timeLogRepository.findById(id);
        return timeLog.orElse(null);
    }

    public TimeLog updateTimeLogEntry(TimeLog timeLog, UpdateTimeLogEntryDTO updateTimeLogEntryDTO) {
        timeLog.setLoggedHours(updateTimeLogEntryDTO.getLoggedHours());
        timeLog.setDescription(updateTimeLogEntryDTO.getDescription());
        timeLog.setStatus(submitted);
        return timeLogRepository.save(timeLog);
    }
}
