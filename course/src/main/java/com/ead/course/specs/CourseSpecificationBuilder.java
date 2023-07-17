package com.ead.course.specs;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.models.CourseUser_;
import com.ead.course.models.Course_;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Objects.nonNull;

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
            specification = specification.and(byCourseUserId(courseFilter.userId()));
        }

        return specification;
    }

    private static Specification<Course> byCourseType(final CourseLevel courseLevel) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Course_.COURSE_LEVEL), courseLevel);
    }

    private static Specification<Course> byCourseStatus(final CourseStatus courseStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Course_.COURSE_STATUS), courseStatus);
    }

    private static Specification<Course> byName(final String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Course_.NAME), "%" + name + "%");
    }

    private static Specification<Course> byCourseUserId(final UUID userId) {
        return (root, query, criteriaBuilder) -> {
            Join<Course, CourseUser> courseUsers = root.join(Course_.COURSES_USERS);
            return criteriaBuilder.equal(courseUsers.get(CourseUser_.USER_ID), userId);
        };
    }

}
