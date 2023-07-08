package com.ead.course.specs;

import com.ead.course.models.Course;
import com.ead.course.models.Course_;
import com.ead.course.models.Module;
import com.ead.course.models.Module_;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModuleSpecificationBuilder {

    public static Specification<Module> toSpec(final ModuleFilter moduleFilter) {
        Specification<Module> specification =
                Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (nonNull(moduleFilter.title())) {
            specification = byTitle(moduleFilter.title()).and(specification);
        }

        if (nonNull(moduleFilter.courseId())) {
            specification = byCourseId(moduleFilter.courseId()).and(specification);
        }

        return specification;
    }

    private static Specification<Module> byTitle(final String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Module_.TITLE), "%" + title + "%");
    }

    private static Specification<Module> byCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            Join<Module, Course> course = root.join(Module_.COURSE);
            return criteriaBuilder.equal(course.get(Course_.COURSE_ID), courseId);
        };
    }

}
