package com.webknot.webtrak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * An entity class.
 */

@Getter
@Setter
@Entity
@Table(name = "users")
public class Project extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_code")
  private String projectCode;

  @Column(name = "project_name")
  private String projectName;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "manager_id")
  @JsonIgnore
  private User manager;

}
