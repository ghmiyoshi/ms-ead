package com.ead.course.services.impl;

import com.ead.course.clients.UserClient;
import com.ead.course.models.CourseUser;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;
    private final UserClient userClient;

    @Override
    public boolean existsByCourseAndUserId(final UUID courseId, final UUID uuid) {
        return courseUserRepository.existsByCourse_CourseIdAndUserId(courseId, uuid);
    }

    @Override
    public CourseUser saveSubscriptionUserInCourse(final CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }

    @Transactional
    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser) {
        var courseUserSaved = courseUserRepository.save(courseUser);
        userClient.postSubscriptionUserInCourse(courseUserSaved.getCourse().getCourseId(),
                                                courseUserSaved.getUserId());
        return courseUserSaved;
    }

}
