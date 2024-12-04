package com.webknot.webtrak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity implements Serializable {

  @Getter
  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Getter
  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

}