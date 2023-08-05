package com.ead.course.controllers;

import com.ead.course.clients.UserClient;
import com.ead.course.dtos.SubscriptionDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.models.CourseUser;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private final UserClient userClient;
    private final CourseService courseService;
    private final CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PathVariable final UUID courseId,
                                                             @PageableDefault(page = 0, size = 10, sort = "userId",
                                                                     direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(userClient.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable final UUID courseId,
                                                               @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        userClient.getUserByIdAndValidateStatus(subscriptionDTO.userId());
        var course = courseService.findCourseById(courseId);
        if (courseUserService.existsByCourseAndUserId(course.getCourseId(), subscriptionDTO.userId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserService.saveAndSendSubscriptionUserInCourse(new CourseUser(course,
                                                                                                                                   subscriptionDTO.userId())));
    }

}
