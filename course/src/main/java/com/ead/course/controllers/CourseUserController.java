package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDTO;
import com.ead.course.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseService courseService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(@PathVariable final UUID courseId, @PageableDefault(sort =
            "userId", direction = Sort.Direction.ASC) final Pageable pageable) {
        courseService.findCourseById(courseId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable final UUID courseId,
                                                               @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        var course = courseService.findCourseById(courseId);
        // TODO verificacoes state transfer
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @DeleteMapping("/courses/users/{userId}")
    public ResponseEntity<Object> deleteUserCourseByUser(@PathVariable final UUID userId) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found");
    }

}
