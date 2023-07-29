package com.ead.course.clients;

import com.ead.course.dtos.ResponsePageDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.UtilsService;
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
public class UserClient {

    private final UtilsService utilsService;
    private final RestTemplate restTemplate;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        final var url = utilsService.createUrl(courseId, pageable);
        log.info("Request URL: {} ", url);
        ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
    }

}
