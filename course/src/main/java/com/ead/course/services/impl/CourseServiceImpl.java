package com.ead.course.services.impl;

import com.ead.course.models.Course;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleService moduleService;

    @Transactional
    @Override
    public void deleteCourse(final UUID courseId) {
        var course = findCourseById(courseId);
        if (nonNull(course.getModules()) || !course.getModules().isEmpty()) {
            course.getModules().stream().forEach(moduleService::deleteModule);
        }
        courseRepository.delete(course);
    }

    @Override
    public Course saveCourse(final Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(final UUID courseId) {
        return courseRepository.findByCourseId(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Page<Course> findAllCourses(final Specification<Course> spec, final Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

}
