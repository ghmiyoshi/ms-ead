package com.ead.course.repositories;

import com.ead.course.models.Lesson;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, UUID>,
    JpaSpecificationExecutor<Lesson> {

  @Query("SELECT l FROM Lesson l JOIN FETCH l.module m JOIN FETCH m.course c"
      + " WHERE m.moduleId = :moduleId AND l.lessonId = :lessonId")
  Optional<Lesson> findLessonByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId);

  @EntityGraph(attributePaths = "module")
  Page<Lesson> findAll(final Specification<Lesson> spec, final Pageable pageable);
}
