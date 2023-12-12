package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.services.UserService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

  private CourseClient courseClient;
  private UserService userService;

  @PreAuthorize("hasRole('STUDENT')")
  @GetMapping("/users/{userId}/courses")
  public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
      @PageableDefault(page = 0, size = 10, sort = "courseId",
          direction = Sort.Direction.ASC) final Pageable pageable,
      @PathVariable final UUID userId,
      @RequestHeader("Authorization") final String token) {
    userService.findById(userId);
    return ResponseEntity.ok(courseClient.getAllCoursesByUser(pageable, userId, token));
  }
}
