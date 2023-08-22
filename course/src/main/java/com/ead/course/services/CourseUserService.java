package com.ead.course.services;

import com.ead.course.models.CourseUser;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(UUID courseId, UUID uuid);

    CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser);

    boolean existsByUserId(UUID userId);

    void deleteUserCourseByUser(UUID userId);

}
