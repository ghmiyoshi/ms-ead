package com.ead.course.specs;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CourseFilter(CourseLevel courseLevel, CourseStatus courseStatus, String name,
                           UUID userId) {

  public static CourseFilter createFilter(final CourseLevel courseLevel,
      final CourseStatus courseStatus,
      final String name,
      final UUID userId) {
    return CourseFilter.builder().courseLevel(courseLevel).courseStatus(courseStatus).name(name)
        .userId(userId).build();
  }

}
