package com.ead.authuser.services.impl;

import com.ead.authuser.services.UtilsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Value("${ead.api.url.course}")
    private String requestUri;

    @Override
    public String createUrl(final UUID userId, final Pageable pageable) {
        return String.format("%s/courses?userId=%s&page=%d&size=%d&sort=%s",
                             requestUri,
                             userId,
                             pageable.getPageNumber(),
                             pageable.getPageSize(),
                             pageable.getSort().toString().replace(": ", ","));
    }

    @Override
    public String deleteUserInCourse(final UUID userId) {
        return String.format("%s/courses/users/%s", requestUri, userId);
    }

}
