package com.ead.course.services.impl;

import com.ead.course.enums.UserStatus;
import com.ead.course.models.User;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  @Override
  public void deleteById(UUID userId) {
    var user = findById(userId);
    courseRepository.deleteCourseUserByUser(userId);
    userRepository.delete(user);
  }

  @Override
  public User findById(UUID userInstructor) {
    return userRepository.findById(userInstructor)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "User not found"));
  }

  @Override
  public void isBlocked(User user) {
    if (UserStatus.BLOCKED.name().equals(user.getUserStatus())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User is blocked");
    }
  }
}
