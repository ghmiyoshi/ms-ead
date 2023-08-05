package com.ead.course.services;

import com.ead.course.models.CourseUser;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(final UUID courseId, final UUID uuid);

    CourseUser saveSubscriptionUserInCourse(final CourseUser courseUser);

    CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser);
    
}
