package com.ead.course.services.impl;

import com.ead.course.models.Lesson;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public void deleteLessons(final Set<Lesson> lessons) {
        if (nonNull(lessons) || !lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson findByModuleIdAndLessonId(final UUID moduleId, final UUID lessonId) {
        return lessonRepository.findLessonByModuleIdAndLessonId(moduleId, lessonId).orElseThrow(() -> new RuntimeException(
                "Lesson not found for this module"));
    }

    @Override
    public Page<Lesson> findAllLessonsByModuleId(final Specification<Lesson> spec, final Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

}
