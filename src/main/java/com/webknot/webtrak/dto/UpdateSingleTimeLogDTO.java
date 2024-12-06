package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSingleTimeLogDTO {
    private Long timeLogId;
    private String status;
    private String approverEmail;
}
