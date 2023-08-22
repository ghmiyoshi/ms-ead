package com.ead.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrlGetAllUsersByCourse(final UUID courseId, final Pageable pageable);

    String createUrlGetUser(final UUID userId);

    String subscriptionUserInCourse(final UUID userId);

    String deleteUserInCourse(UUID courseId);

}
