package com.ead.course.controllers;

import static com.ead.course.specs.UserSpecificationBuilder.toSpec;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specs.UserFilter;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

  private final CourseService courseService;
  private final UserService userService;

  @PreAuthorize("hasRole('INSTRUCTOR')")
  @GetMapping("/courses/{courseId}/users")
  public ResponseEntity<Object> getAllUsersByCourse(
      @RequestParam(required = false) final String userType,
      @RequestParam(required = false) final String userStatus,
      @RequestParam(required = false) final String email,
      @RequestParam(required = false) final String fullName,
      @PathVariable final UUID courseId,
      @PageableDefault(sort = "userId", direction =
          Sort.Direction.ASC) final Pageable pageable) {
    var userFilter = UserFilter.createFilter(userType, userStatus, email, fullName, courseId);
    courseService.findCourseById(courseId);
    return ResponseEntity.ok(courseService.findAllUsersByCourse(toSpec(userFilter), pageable));
  }

  @PreAuthorize("hasRole('STUDENT')")
  @PostMapping("/courses/{courseId}/users/subscription")
  public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable final UUID courseId,
      @RequestBody @Valid final SubscriptionDto subscriptionDto) {
    var course = courseService.findCourseById(courseId);
    var user = userService.findById(subscriptionDto.userId());
    user.isBlocked();

    courseService.existsByCourseAndUser(courseId, subscriptionDto.userId());
    courseService.saveSubscriptionUserInCourseAndSendNotification(course, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Subscription " +
            "created"));
  }
}
