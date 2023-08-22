package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private CourseClient courseClient;
    private UserCourseService userCourseService;
    private UserService userService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId",
            direction = Sort.Direction.ASC) final Pageable pageable, @PathVariable final UUID userId) {
        userService.findById(userId);
        return ResponseEntity.ok(courseClient.getAllCoursesByUser(pageable, userId));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable final UUID userId,
                                                          @RequestBody @Valid final UserCourseDTO userCourseDTO) {
        var user = userService.findById(userId);
        if (userCourseService.existsByUserAndCourseId(userId, userCourseDTO.courseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseService.saveSubscriptionUserIndCourse(user,
                                                                                                              userCourseDTO.courseId()));
    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<?> deleteUserCourseByCourse(@PathVariable final UUID courseId) {
        if (userCourseService.existsByCourseId(courseId)) {
            userCourseService.deleteUserCourseByCourse(courseId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: UserCourse not found!");
    }

}
