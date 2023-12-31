package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class CourseClient {

  private final UtilsService utilsService;
  private final RestTemplate restTemplate;

  // @Retry(name = "retryInstance", fallbackMethod = "getAllCoursesFallback")
  @CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "getAllCoursesFallback")
  public Page<CourseDto> getAllCoursesByUser(final Pageable pageable, final UUID userId,
      final String token) {
    log.info("[method:getAllCoursesByUser] userId: {}", userId);
    final var url = utilsService.createUrl(userId, pageable);
    log.info("[method:getAllCoursesByUser] url: {} ", url);
    ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType =
        new ParameterizedTypeReference<>() {
        };
    final var headers = new HttpHeaders();
    headers.set("Authorization", token);
    final var requestEntity = new HttpEntity<>("parameters", headers);
    return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
  }

  public Page<CourseDto> getAllCoursesFallback(final Pageable pageable,
      final UUID userId,
      final Throwable throwable) {
    log.error("[method:getAllCoursesFallback] Inside retry fallback, cause {}",
        throwable.getMessage());
    return Page.empty();
  }
}
