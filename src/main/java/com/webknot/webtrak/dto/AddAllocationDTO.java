package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAllocationDTO {
    private String employeeEmail;
    private Long projectId;
    private String role;
    private Long allocatedHours;
}
