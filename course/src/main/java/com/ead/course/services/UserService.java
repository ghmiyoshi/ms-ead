package com.ead.course.services;

import com.ead.course.models.User;
import java.util.UUID;

public interface UserService {

  User save(User user);

  void deleteById(UUID userId);

  User findById(UUID userInstructor);
}
