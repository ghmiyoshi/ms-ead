package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

  private UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/subscription")
  public ResponseEntity<Object> saveSubscriptionInstructor(
      @RequestBody @Valid final InstructorDto instructorDto) {
    var user = userService.findById(instructorDto.userId());
    userService.subscriptionInstructor(user);
    user = userService.updateUser(user);
    return ResponseEntity.ok().body(user);
  }
}
