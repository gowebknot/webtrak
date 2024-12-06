package com.webknot.webtrak.controller;

import com.webknot.webtrak.dto.*;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.TimeLog;
import com.webknot.webtrak.entity.User;
import com.webknot.webtrak.exception.BadRequestException;
import com.webknot.webtrak.service.AllocationsService;
import com.webknot.webtrak.service.ProjectService;
import com.webknot.webtrak.service.TimeLogService;
import com.webknot.webtrak.service.UserService;
import com.webknot.webtrak.util.Utils;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * This is TemplateServiceController class.
 * It will have all API end points definition.
 */
@RestController
public class TimeLogServiceController extends BaseController {

    private final AllocationsService allocationsService;

    private final TimeLogService timeLogService;
    private final UserService userService;

    public TimeLogServiceController(AllocationsService allocationsService,
                                    TimeLogService timeLogService, UserService userService) {
        this.allocationsService = allocationsService;
        this.timeLogService = timeLogService;
        this.userService = userService;
    }

    @PostMapping("timelog")
    public TimeLog addTimeLog(@RequestHeader("Authorization") String authorization,
                              @RequestBody AddTimeLogDTO addTimeLogDTO) throws ParseException {

        User user = userService.getUserByEmail(addTimeLogDTO.getEmployeeEmail());

        if (user == null) {
            throw new BadRequestException("Employee not found");
        }

        Utils.verifyPassword(authorization, user.getPrivateKey());

        return timeLogService.addTimeLog(addTimeLogDTO, user);

    }

    @GetMapping(value = "timelog/get/{projectCode}/{empEmail}/{date}")
    public TimeLogListOutputDTO getTimeLogs(@PathVariable(value = "projectCode") String projectCode,
                                        @PathVariable(value = "empEmail") String empEmail,
                                        @PathVariable(value = "date") String date) throws ParseException {

        User user = userService.getUserByEmail(empEmail);

        if (user == null) {
            throw new BadRequestException("Employee not found");
        }

        return timeLogService.getTimeLogsByDateAndUserAndProject(date, user, projectCode);

    }

    @GetMapping(value = "timelog/get/{projectCode}/{empEmail}")
    public TimeLogListOutputDTO getTimeLogsByUserAndProject(@PathVariable("projectCode") String projectCode,
                                     @PathVariable("empEmail") String empEmail) throws ParseException {

        User user = userService.getUserByEmail(empEmail);

        if (user == null) {
            throw new BadRequestException("Employee not found");
        }

        return timeLogService.getTimeLogsByUserAndProject(user, projectCode);

    }

    @PutMapping("timelog/status")
    public TimeLog updateTimelogStatus(@RequestHeader("Authorization") String authorization,
                                       @RequestBody UpdateSingleTimeLogDTO updateTimeLogDTO) throws ParseException {
        User approver = userService.getUserByEmail(updateTimeLogDTO.getApproverEmail());

        if (approver == null) {
            throw new BadRequestException("Approver not found");
        }

        Utils.verifyPassword(authorization, approver.getPrivateKey());

        return timeLogService.updateTimeLogStatus(updateTimeLogDTO.getTimeLogId(),
                updateTimeLogDTO.getStatus(), approver);
    }

    @PutMapping("timelog/status/batch")
    public void updateTimelogStatusBatch(@RequestHeader("Authorization") String authorization,
                                         @RequestBody UpdateTimeLogDTO updateTimeLogDTO) throws ParseException {
        User emp = userService.getUserByEmail(updateTimeLogDTO.getEmployeeEmail());
        User approver = userService.getUserByEmail(updateTimeLogDTO.getApproverEmail());

        if (emp == null || approver == null) {
            throw new BadRequestException("Employee/Approver not found");
        }

        Utils.verifyPassword(authorization, approver.getPrivateKey());

        timeLogService.updateTimeLogStatusByDateAndUserAndProject(updateTimeLogDTO, emp, approver);
    }

    @PutMapping("timelog/entry")
    public TimeLog updateTimelogEntry(@RequestHeader("Authorization") String authorization,
                                   @RequestBody UpdateTimeLogEntryDTO updateTimeLogEntryDTO) throws ParseException {

        TimeLog timelog = timeLogService.getTimeLogById(updateTimeLogEntryDTO.getTimeLogId());
        if (timelog == null) {
            throw new BadRequestException("TimeLog not found");
        }

        User emp = timelog.getUser();

        Utils.verifyPassword(authorization, emp.getPrivateKey());

        return timeLogService.updateTimeLogEntry(timelog, updateTimeLogEntryDTO);
    }

}
