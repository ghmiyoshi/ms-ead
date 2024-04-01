package com.ead.authuser.services;

import com.ead.authuser.dtos.UserRequestDto;
import com.ead.authuser.models.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<User> findAll(Pageable pageable);

  User findById(UUID userId);

  void deleteById(UUID userId);

  User save(User user);

  void validateUser(UserRequestDto userRequest);

  User newStudent(UserRequestDto userRequest);

  void subscriptionInstructor(User user);

  void updateFullNameAndPhoneNumber(User user, UserRequestDto userRequest);

  void updatePassword(User user, UserRequestDto userRequest);

  void updateImageUrl(User user, String imageUrl);

  User saveUser(User user);

  void deleteUser(UUID userId);

  User updateUser(User user);

  User updatePasswordUser(User user);
}
