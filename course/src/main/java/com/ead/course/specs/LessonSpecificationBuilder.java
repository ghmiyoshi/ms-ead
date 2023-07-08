package com.ead.course.specs;

import com.ead.course.models.Lesson;
import com.ead.course.models.Lesson_;
import com.ead.course.models.Module;
import com.ead.course.models.Module_;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonSpecificationBuilder {

    public static Specification<Lesson> toSpec(final LessonFilter lessonFilter) {
        Specification<Lesson> specification =
                Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (nonNull(lessonFilter.title())) {
            specification = byTitle(lessonFilter.title()).and(specification);
        }

        if (nonNull(lessonFilter.moduleId())) {
            specification = byModuleId(lessonFilter.moduleId()).and(specification);
        }

        return specification;
    }

    private static Specification<Lesson> byTitle(final String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Lesson_.TITLE), "%" + title + "%");
    }

    private static Specification<Lesson> byModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            Join<Lesson, Module> module = root.join(Lesson_.MODULE);
            return criteriaBuilder.equal(module.get(Module_.MODULE_ID), moduleId);
        };
    }

}
