package com.bellboy.template.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * An entity class.
 */

@Getter
@Setter
@Entity
@Table(name = "entity_class_name")
public class EntityClassName extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //TODO - Define entity here.

}
