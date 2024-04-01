package com.ead.course.specs;

import static java.util.Objects.nonNull;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.models.Course_;
import com.ead.course.models.User;
import com.ead.course.models.User_;
import jakarta.persistence.criteria.Join;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseSpecificationBuilder {

  public static Specification<Course> toSpec(final CourseFilter courseFilter) {
    Specification<Course> specification =
        Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

    if (nonNull(courseFilter.courseLevel())) {
      specification = specification.and(byCourseType(courseFilter.courseLevel()));
    }

    if (nonNull(courseFilter.courseStatus())) {
      specification = specification.and(byCourseStatus(courseFilter.courseStatus()));
    }

    if (nonNull(courseFilter.name())) {
      specification = specification.and(byName(courseFilter.name()));
    }

    if (nonNull(courseFilter.userId())) {
      specification = specification.and(byUserId(courseFilter.userId()));
    }

    return specification;
  }

  private static Specification<Course> byCourseType(final CourseLevel courseLevel) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Course_.COURSE_LEVEL),
        courseLevel);
  }

  private static Specification<Course> byCourseStatus(final CourseStatus courseStatus) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Course_.COURSE_STATUS),
        courseStatus);
  }

  private static Specification<Course> byName(final String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Course_.NAME),
        "%" + name + "%");
  }

  private static Specification<Course> byUserId(final UUID userId) {
    return (root, query, criteriaBuilder) -> {
      Join<Course, User> users = root.join(Course_.USERS);
      return criteriaBuilder.equal(users.get(User_.USER_ID), userId);
    };
  }
}
