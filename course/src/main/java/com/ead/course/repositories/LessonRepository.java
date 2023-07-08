package com.ead.course.repositories;

import com.ead.course.models.Lesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("SELECT l FROM Lesson l JOIN FETCH l.module m JOIN FETCH m.course c"
            + " WHERE m.moduleId = :moduleId AND l.lessonId = :lessonId")
    Optional<Lesson> findLessonByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId);

    @EntityGraph(attributePaths = "module")
    List<Lesson> findAllLessonsByModuleModuleId(final UUID moduleId);

}
