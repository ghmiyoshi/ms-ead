package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CourseClient {

    private static final String REQUEST_URL = "Request URL: {} ";
    private final UtilsService utilsService;
    private final RestTemplate restTemplate;

    public Page<CourseDTO> getAllCoursesByUser(final Pageable pageable, final UUID userId) {
        log.info("{}::getAllCoursesByUser - user id received: {}", getClass().getSimpleName(), userId);
        final var url = utilsService.createUrl(userId, pageable);
        log.info("{}::getAllCoursesByUser - Request URL: {} ", getClass().getSimpleName(), url);
        ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
    }

    public void deleteUserInCourse(final UUID userId) {
        final var url = utilsService.deleteUserInCourse(userId);
        log.info(REQUEST_URL, url);
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }

}
