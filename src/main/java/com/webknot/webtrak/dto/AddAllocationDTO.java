package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAllocationDTO {
    private String employeeEmail;
    private String projectCode;
    private String role;
    private Long allocatedHours;
    private String startDate;
    private String managerEmail;
}
