package com.ead.course.services.impl;

import com.ead.course.clients.UserClient;
import com.ead.course.models.CourseUser;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;
    private final UserClient userClient;

    @Override
    public boolean existsByCourseAndUserId(final UUID courseId, final UUID userId) {
        log.info("{}::existsByCourseAndUserId - Course id: {} and user id: {}", getClass().getSimpleName(), courseId,
                 userId);
        return courseUserRepository.existsByCourse_CourseIdAndUserId(courseId, userId);
    }

    @Transactional
    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(final CourseUser courseUser) {
        log.info("{}::saveAndSendSubscriptionUserInCourse - received: {}", getClass().getSimpleName(), courseUser);
        var courseUserSaved = courseUserRepository.save(courseUser);
        userClient.postSubscriptionUserInCourse(courseUserSaved.getCourse().getCourseId(),
                                                courseUserSaved.getUserId());
        return courseUserSaved;
    }

    @Override
    public boolean existsByUserId(final UUID userId) {
        return courseUserRepository.existsByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteUserCourseByUser(final UUID userId) {
        courseUserRepository.deleteAllByUserId(userId);
    }

}
