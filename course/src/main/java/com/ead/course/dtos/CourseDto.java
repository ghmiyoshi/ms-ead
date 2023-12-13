package com.ead.course.dtos;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.utils.ObjectMapperUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CourseDto(
    @NotBlank
    String name,
    @NotBlank
    String description,
    String imageUrl,
    @NotNull
    CourseStatus courseStatus,
    @NotNull
    UUID userInstructor,
    @NotNull
    CourseLevel courseLevel
) {

  @Override
  public String toString() {
    return ObjectMapperUtils.writeObjectInJson(this);
  }

}
