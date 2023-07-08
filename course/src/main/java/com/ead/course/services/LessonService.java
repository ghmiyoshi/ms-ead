package com.ead.course.services;

import com.ead.course.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.UUID;

public interface LessonService {

    void deleteLessons(final Set<Lesson> lessons);

    void deleteLesson(final Lesson lesson);

    Lesson saveLesson(final Lesson lesson);

    Lesson findByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId);

    Page<Lesson> findAllLessonsByModuleId(final Specification<Lesson> spec, final Pageable pageable);

}
