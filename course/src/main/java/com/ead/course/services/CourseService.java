package com.ead.course.services;

import com.ead.course.models.Course;
import com.ead.course.models.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourseService {

  void deleteCourse(UUID uuid);

  Course saveCourse(Course course);

  Course findCourseById(UUID courseId);

  Page<Course> findAllCourses(Specification<Course> spec, Pageable pageable);

  Page<User> findAllUsersByCourse(Specification<User> spec, Pageable pageable);

  void existsByCourseAndUser(UUID courseId, UUID userId);

  void saveSubscriptionUserInCourse(UUID courseId, UUID userId);

  void saveSubscriptionUserInCourseAndSendNotification(Course course, User user);
}
