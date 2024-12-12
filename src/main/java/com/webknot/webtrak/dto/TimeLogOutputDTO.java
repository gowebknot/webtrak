package com.webknot.webtrak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class TimeLogOutputDTO {
    private Long id;
    private String projectCode;
    private String userEmail;
    private Long loggedHours;
    private String description;
    private Date date;
    private String status;
}
