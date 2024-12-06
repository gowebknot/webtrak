package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAllocationDTO {
    private String employeeEmail;
    private String projectCode;
    private String role;
    private Long allocatedHours;
    private String startDate;
    private boolean isActive = true;
    private String managerEmail;
}
