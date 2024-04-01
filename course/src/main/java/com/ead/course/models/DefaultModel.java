package com.ead.course.models;

import com.ead.course.utils.ObjectMapperUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class DefaultModel {

  @JsonIgnore
  @CreationTimestamp
  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private OffsetDateTime creationDate;

  @JsonIgnore
  @UpdateTimestamp
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private OffsetDateTime lastUpdateDate;

  @Override
  public String toString() {
    return ObjectMapperUtils.writeObjectInJson(this);
  }
}
