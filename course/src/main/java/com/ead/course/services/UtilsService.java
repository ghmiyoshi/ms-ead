package com.ead.course.services;

import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface UtilsService {

  String createUrlGetAllUsersByCourse(final UUID courseId, final Pageable pageable);

  String createUrlGetUser(final UUID userId);

  String subscriptionUserInCourse(final UUID userId);

  String deleteUserInCourse(UUID courseId);
}
