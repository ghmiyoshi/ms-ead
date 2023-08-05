package com.ead.course.repositories;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID>, JpaSpecificationExecutor<Course> {

    boolean existsByCourse_CourseIdAndUserId(final UUID courseId, final UUID userId);

}
