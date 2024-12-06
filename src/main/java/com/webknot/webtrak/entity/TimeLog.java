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
@Table(name = "time_log")
public class TimeLog extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "project_id")
  @JsonIgnore
  private Project project;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "logged_hours")
  private Long loggedHours;

  @Column(name = "description")
  private String description;

  @Column(name = "date")
  private Date date;

  @Column(name = "status")
  private String status;

}
