package com.ead.course.repositories;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID>, JpaSpecificationExecutor<Course> {

    boolean existsByCourse_CourseIdAndUserId(UUID courseId, UUID userId);

    @Query("SELECT c FROM CourseUser c WHERE c.course.courseId = :courseId")
    List<CourseUser> findAllCourseUserIntoCourse(UUID courseId);

    boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);

}
