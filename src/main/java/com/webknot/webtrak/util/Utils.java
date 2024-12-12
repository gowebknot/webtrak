package com.webknot.webtrak.util;

import com.webknot.webtrak.dto.AllocationOutputDTO;
import com.webknot.webtrak.dto.TimeLogOutputDTO;
import com.webknot.webtrak.entity.Allocation;
import com.webknot.webtrak.entity.TimeLog;
import com.webknot.webtrak.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void verifyPassword(String str, String match) {
        if (!str.equalsIgnoreCase(match)) {
            throw new BadRequestException("Not Authorized");
        }
    }

    public static List<AllocationOutputDTO> getAllocationOutputDTO(List<Allocation> allocations) {

        List<AllocationOutputDTO> allocationOutputs = new ArrayList<>();

        for (Allocation allocation : allocations) {
            allocationOutputs.add(AllocationOutputDTO.builder()
                    .id(allocation.getId())
                    .role(allocation.getRole())
                    .endDate(allocation.getEndDate())
                    .startDate(allocation.getStartDate())
                    .projectCode(allocation.getProject().getProjectCode())
                    .userEmail(allocation.getUser().getEmail())
                    .allocatedHours(allocation.getAllocatedHours())
                    .isActive(allocation.getIsActive())
                    .build());
        }
        return allocationOutputs;
    }

    public static List<TimeLogOutputDTO> getTimeLogsOutput(List<TimeLog> timelogs) {
        List<TimeLogOutputDTO> timeLogOutputDTOS = new ArrayList<>();

        for (TimeLog timeLog : timelogs) {
            timeLogOutputDTOS.add(TimeLogOutputDTO.builder()
                    .id(timeLog.getId())
                    .projectCode(timeLog.getProject().getProjectCode())
                    .userEmail(timeLog.getUser().getEmail())
                    .loggedHours(timeLog.getLoggedHours())
                    .status(timeLog.getStatus())
                    .description(timeLog.getDescription())
                    .build());
        }
        return timeLogOutputDTOS;
    }
}
