package com.ead.course.clients;

import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.ResponsePageDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static com.ead.course.enums.UserStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserClient {

    private static final String REQUEST_URL = "Request URL: {} ";
    private final UtilsService utilsService;
    private final RestTemplate restTemplate;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        final var url = utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.info(REQUEST_URL, url);
        ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
    }

    public UserDTO getUserByIdAndValidateStatus(final UUID userId) {
        final var user = getUserById(userId);
        if (ACTIVE.equals(user.userStatus())) {
            log.info("{}::getUserByIdAndValidateStatus - Active user id: {}", getClass().getSimpleName(),
                     user.userId());
            return user;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not active");
    }

    public UserDTO getUserById(final UUID userId) {
        final var url = utilsService.createUrlGetUser(userId);
        log.info(REQUEST_URL, url);
        var user = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class).getBody();
        return Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void postSubscriptionUserInCourse(final UUID courseId, final UUID userId) {
        final var url = utilsService.subscriptionUserInCourse(userId);
        log.info(REQUEST_URL, url);
        restTemplate.postForObject(url, new CourseUserDTO(courseId), String.class);
    }

    public void deleteCourseInAuthUser(final UUID courseId) {
        final var url = utilsService.deleteUserInCourse(courseId);
        log.info(REQUEST_URL, url);
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }

}
