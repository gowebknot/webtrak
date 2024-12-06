package com.webknot.webtrak.dto;

import com.webknot.webtrak.entity.TimeLog;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class TimeLogListOutputDTO {
    private long size;
    private Long totalHours;
    private String date;
    private List<TimeLog> timeLogs;
}
