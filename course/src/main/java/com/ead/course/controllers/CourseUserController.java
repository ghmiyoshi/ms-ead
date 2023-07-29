package com.ead.course.controllers;

import com.ead.course.clients.UserClient;
import com.ead.course.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final UserClient userClient;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PathVariable final UUID courseId,
                                                             @PageableDefault(page = 0, size = 10, sort = "userId",
                                                                     direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(userClient.getAllUsersByCourse(courseId, pageable));
    }

}
