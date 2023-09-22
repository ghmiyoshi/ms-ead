package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDTO;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specs.UserFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.ead.course.specs.UserSpecificationBuilder.toSpec;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseService courseService;
    private final UserService userService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(@RequestParam(required = false) final String userType,
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

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable final UUID courseId,
                                                               @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        courseService.findCourseById(courseId);
        
        var user = userService.findById(subscriptionDTO.userId());
        userService.isBlocked(user);

        courseService.existsByCourseAndUser(courseId, subscriptionDTO.userId());
        courseService.saveSubscriptionUserInCourse(courseId, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created");
    }

    @DeleteMapping("/courses/users/{userId}")
    public ResponseEntity<Object> deleteUserCourseByUser(@PathVariable final UUID userId) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found");
    }

}
