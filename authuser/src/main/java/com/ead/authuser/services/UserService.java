package com.ead.authuser.services;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<User> findAll(final Pageable pageable);

    User findById(final UUID userId);

    void deleteById(final UUID userId);

    User save(User user);

    void validateUser(UserRequestDTO userRequest);

}
