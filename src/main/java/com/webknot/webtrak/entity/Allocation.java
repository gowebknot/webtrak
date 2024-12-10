package com.webknot.webtrak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Entity
@Table(name = "allocations")
public class Allocation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "allocated_hours")
  private Long allocatedHours;

  @Column(name = "role")
  private String role;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "is_active")
  private Boolean isActive = true;


}
