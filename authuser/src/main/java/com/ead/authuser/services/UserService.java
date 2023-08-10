package com.ead.authuser.services;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    User findById(UUID userId);

    void deleteById(UUID userId);

    User save(User user);

    void validateUser(UserRequestDTO userRequest);

    User newStudent();

    void subscriptionInstructor(User user);

    void updateFullNameAndPhoneNumber(User user, UserRequestDTO userRequest);

    void updatePassword(User user, UserRequestDTO userRequest);

    void updateImageUrl(User user, String imageUrl);

}
