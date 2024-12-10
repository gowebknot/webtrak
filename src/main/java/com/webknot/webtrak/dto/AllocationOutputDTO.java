package com.webknot.webtrak.dto;

import com.webknot.webtrak.entity.BaseEntity;
import com.webknot.webtrak.entity.Project;
import com.webknot.webtrak.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * An entity class.
 */

@Getter
@Setter
@Builder
public class AllocationOutputDTO {

  private Long id;

  private String projectCode;

  private String userEmail;

  private Long allocatedHours;

  private String role;

  private Date startDate;

  private Date endDate;

  private Boolean isActive;

}
