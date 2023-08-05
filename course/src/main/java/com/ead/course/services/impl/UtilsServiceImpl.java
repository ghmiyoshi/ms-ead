package com.ead.course.services.impl;

import com.ead.course.services.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Value("${ead.api.course.url}")
    private String requestUri;

    @Override
    public String createUrlGetAllUsersByCourse(final UUID courseId, final Pageable pageable) {
        return String.format("%s/users?courseId=%s&page=%d&size=%d&sort=%s", requestUri, courseId,
                             pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString().replace(
                        ": ", ","));
    }

    @Override
    public String createUrlGetUser(final UUID userId) {
        return String.format("%s/users/%s", requestUri, userId);
    }

    @Override
    public String subscriptionUserInCourse(final UUID userId) {
        return String.format("%s/users/%s/courses/subscription", requestUri, userId);
    }

}
