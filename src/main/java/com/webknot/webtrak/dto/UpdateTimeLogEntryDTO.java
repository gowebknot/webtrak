package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimeLogEntryDTO {
    private Long timeLogId;
    private String description;
    private Long loggedHours;
}
