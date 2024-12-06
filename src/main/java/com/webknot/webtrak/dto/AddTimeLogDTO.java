package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTimeLogDTO {
    private String employeeEmail;
    private String projectCode;
    private String description;
    private Long loggedHours;
    private String date;
}
