package com.ead.authuser.services;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;

import java.util.UUID;

public interface UserCourseService {

    UserCourse saveSubscriptionUserIndCourse(final User user, final UUID courseId);

    boolean existsByUserAndCourseId(final UUID userId, final UUID courseId);

    boolean existsByCourseId(UUID courseId);

    void deleteUserCourseByCourse(UUID courseId);

}
