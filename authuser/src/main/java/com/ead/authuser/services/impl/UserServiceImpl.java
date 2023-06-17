package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Cacheable
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(final UUID userId) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public void deleteById(final UUID userId) {
        var user = this.findById(userId);
        userRepository.delete(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void validateUser(UserRequestDTO userRequest) {
        if (userRepository.existsUserByUsernameOrEmail(userRequest.username(), userRequest.email())) {
            throw new RuntimeException("User is already taken");
        }
    }

}
