package com.ead.course.services;

import com.ead.course.models.Lesson;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LessonService {

  void deleteLessons(final Set<Lesson> lessons);

  void deleteLesson(final Lesson lesson);

  Lesson saveLesson(final Lesson lesson);

  Lesson findByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId);

  Page<Lesson> findAllLessonsByModuleId(final Specification<Lesson> spec, final Pageable pageable);
}
