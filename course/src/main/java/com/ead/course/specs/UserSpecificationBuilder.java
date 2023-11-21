package com.ead.course.specs;

import static java.util.Objects.nonNull;

import com.ead.course.models.Course_;
import com.ead.course.models.User;
import com.ead.course.models.User_;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecificationBuilder {

  public static Specification<User> toSpec(final UserFilter userFilter) {
    Specification<User> specification =
        Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

    if (nonNull(userFilter.userType())) {
      specification = specification.and(byUserType(userFilter.userType()));
    }

    if (nonNull(userFilter.userStatus())) {
      specification = specification.and(byUserStatus(userFilter.userStatus()));
    }

    if (nonNull(userFilter.email())) {
      specification = specification.and(byEmail(userFilter.email()));
    }

    if (nonNull(userFilter.fullName())) {
      specification = specification.and(byFullName(userFilter.fullName()));
    }

    if (nonNull(userFilter.courseId())) {
      specification = specification.and(byCourseId(userFilter.courseId()));
    }

    return specification;
  }

  private static Specification<User> byUserType(final String userType) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_TYPE),
        userType);
  }

  private static Specification<User> byUserStatus(final String userStatus) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_STATUS),
        userStatus);
  }

  private static Specification<User> byEmail(final String email) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL),
        "%" + email + "%");
  }

  private static Specification<User> byFullName(final String fullName) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.FULL_NAME),
        "%" + fullName + "%");
  }

  private static Specification<User> byCourseId(final UUID courseId) {
    return (root, query, criteriaBuilder) -> {
      var courses = root.join(User_.COURSES);
      return criteriaBuilder.equal(courses.get(Course_.COURSE_ID), courseId);
    };
  }
}
