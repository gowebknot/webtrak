package com.webknot.webtrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDTO {
    String projectCode;
    String projectName;
    String managerEmail;
}
