package com.ead.course.services;

import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    void deleteCourse(UUID uuid);

    Course saveCourse(Course course);

    Course findCourseById(UUID courseId);

    Page<Course> findAllCourses(Specification<Course> spec, Pageable pageable);

}
