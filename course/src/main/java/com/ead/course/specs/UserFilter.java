package com.ead.course.specs;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserFilter(String userType, String userStatus, String email, String fullName,
                         UUID courseId) {

  public static UserFilter createFilter(final String userType,
      final String userStatus,
      final String email,
      final String fullName,
      final UUID courseId) {
    return UserFilter.builder().userType(userType).userStatus(userStatus).email(email)
        .fullName(fullName).courseId(courseId).build();
  }
}
