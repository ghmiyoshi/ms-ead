package com.ead.course.specs;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.models.Course_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseSpecificationBuilder {

    public static Specification<Course> toSpec(final CourseFilter courseFilter) {
        Specification<Course> specification =
                Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (nonNull(courseFilter.courseLevel())) {
            specification = byCourseType(courseFilter.courseLevel()).and(specification);
        }

        if (nonNull(courseFilter.courseStatus())) {
            specification = byCourseStatus(courseFilter.courseStatus()).and(specification);
        }

        if (nonNull(courseFilter.name())) {
            specification = byName(courseFilter.name()).and(specification);
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

}
