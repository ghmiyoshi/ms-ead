package com.ead.course.services;

import com.ead.course.models.Lesson;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LessonService {

    void deleteLessons(final Set<Lesson> lessons);

    void deleteLesson(final Lesson lesson);

    Lesson saveLesson(final Lesson lesson);

    Lesson findByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId);

    List<Lesson> findAllLessonsByModuleId(final UUID moduleId);

}
