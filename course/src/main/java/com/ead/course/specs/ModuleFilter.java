package com.ead.course.specs;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ModuleFilter(String title, UUID courseId) {

  public static ModuleFilter createFilter(final String title, final UUID courseId) {
    return ModuleFilter.builder().title(title).courseId(courseId).build();
  }
}
