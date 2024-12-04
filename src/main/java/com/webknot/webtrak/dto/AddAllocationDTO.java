package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAllocationDTO {
    private String employeeEmail;
    private String projectId;
    private String role;
    private Long allocatedHours;
}
