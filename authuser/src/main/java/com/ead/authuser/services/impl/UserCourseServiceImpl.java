package com.ead.authuser.services.impl;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {

    private UserCourseRepository userCourseRepository;

    @Override
    public UserCourse saveSubscriptionUserIndCourse(final User user, final UUID courseId) {
        return userCourseRepository.save(new UserCourse(user, courseId));
    }

    @Override
    public boolean existsByUserAndCourseId(final UUID userId, final UUID courseId) {
        return userCourseRepository.existsByUserUserIdAndCourseId(userId, courseId);
    }

}
