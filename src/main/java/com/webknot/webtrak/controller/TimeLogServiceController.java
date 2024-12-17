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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<GenericResponseDTO> addTimeLog(@RequestHeader("Authorization") String authorization,
                                                          @RequestBody AddTimeLogDTO addTimeLogDTO) throws ParseException {

        User user = userService.getUserByEmail(addTimeLogDTO.getEmployeeEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User not found", null));
        }

        try {
            Utils.verifyPassword(authorization, user.getPrivateKey());
            return ResponseEntity.ok().body(new GenericResponseDTO("success", timeLogService.addTimeLog(addTimeLogDTO, user)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }

    }

    @GetMapping(value = "timelog/get/{projectCode}/{empEmail}/{date}")
    public ResponseEntity<GenericResponseDTO> getTimeLogs(@PathVariable(value = "projectCode") String projectCode,
                                        @PathVariable(value = "empEmail") String empEmail,
                                        @PathVariable(value = "date") String date) throws ParseException {

        User user = userService.getUserByEmail(empEmail);

        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User not found", null));
        }

        try {
            return ResponseEntity.ok().body(new GenericResponseDTO("success", timeLogService
                    .getTimeLogsByDateAndUserAndProject(date, user, projectCode)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }


    }

    @GetMapping(value = "timelog/get/{projectCode}/{empEmail}")
    public ResponseEntity<GenericResponseDTO> getTimeLogsByUserAndProject(@PathVariable("projectCode") String projectCode,
                                     @PathVariable("empEmail") String empEmail) throws ParseException {

        User user = userService.getUserByEmail(empEmail);

        if (user == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User not found", null));
        }

        try {
            return ResponseEntity.ok().body(new GenericResponseDTO("success",
                    timeLogService.getTimeLogsByUserAndProject(user, projectCode)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }

    }

    @PutMapping("timelog/status")
    public ResponseEntity<GenericResponseDTO> updateTimelogStatus(@RequestHeader("Authorization") String authorization,
                                       @RequestBody UpdateSingleTimeLogDTO updateTimeLogDTO) throws ParseException {
        User approver = userService.getUserByEmail(updateTimeLogDTO.getApproverEmail());

        if (approver == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Approver not found", null));
        }

        try {
            Utils.verifyPassword(authorization, approver.getPrivateKey());
            return ResponseEntity.ok().body(new GenericResponseDTO("success",
                    timeLogService.updateTimeLogStatus(updateTimeLogDTO.getTimeLogId(),
                            updateTimeLogDTO.getStatus(), approver)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("timelog/status/batch")
    public ResponseEntity<GenericResponseDTO> updateTimelogStatusBatch(@RequestHeader("Authorization") String authorization,
                                         @RequestBody UpdateTimeLogDTO updateTimeLogDTO) throws ParseException {
        User emp = userService.getUserByEmail(updateTimeLogDTO.getEmployeeEmail());
        User approver = userService.getUserByEmail(updateTimeLogDTO.getApproverEmail());

        if (emp == null || approver == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Employee/Approver not found", null));
        }

        try {
            Utils.verifyPassword(authorization, approver.getPrivateKey());

            timeLogService.updateTimeLogStatusByDateAndUserAndProject(updateTimeLogDTO, emp, approver);
            return ResponseEntity.ok().body(new GenericResponseDTO("success", null));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }

    }

    @PutMapping("timelog/entry")
    public ResponseEntity<GenericResponseDTO> updateTimelogEntry(@RequestHeader("Authorization") String authorization,
                                   @RequestBody UpdateTimeLogEntryDTO updateTimeLogEntryDTO) throws ParseException {

        TimeLog timelog = timeLogService.getTimeLogById(updateTimeLogEntryDTO.getTimeLogId());
        if (timelog == null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("Timelog not found", null));
        }

        User emp = timelog.getUser();



        try {
            Utils.verifyPassword(authorization, emp.getPrivateKey());
            return ResponseEntity.ok().body(new GenericResponseDTO("success",
                    timeLogService.updateTimeLogEntry(timelog, updateTimeLogEntryDTO)));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GenericResponseDTO(e.getMessage(), null));
        }
    }

}
