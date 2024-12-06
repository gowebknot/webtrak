package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimeLogDTO {
    private String employeeEmail;
    private String projectCode;
    private String date;
    private String status;
    private String approverEmail;
}
