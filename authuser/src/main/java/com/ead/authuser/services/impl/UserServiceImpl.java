package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserCourseRepository userCourseRepository;

    @Cacheable
    @Override
    public Page<User> findAll(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public void deleteById(final UUID userId) {
        var user = this.findById(userId);
        List<UserCourse> userCourses = userCourseRepository.findAllByUserUserId(userId);
        if (nonNull(userCourses) && !userCourses.isEmpty()) {
            userCourseRepository.deleteAll(userCourses);
        }
        userRepository.delete(user);
    }

    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    public void validateUser(final UserRequestDTO userRequest) {
        if (userRepository.existsUserByUsernameOrEmail(userRequest.username(), userRequest.email())) {
            log.error("{}::validateUser - User is already taken", getClass().getSimpleName());
            throw new RuntimeException("User is already taken");
        }
    }

    @Override
    public User newStudent() {
        var user = new User();
        user.setUserType(UserType.STUDENT);
        return user;
    }

    @Override
    public void subscriptionInstructor(final User user) {
        user.setUserType(UserType.INSTRUCTOR);
    }

    @Override
    public void updateFullNameAndPhoneNumber(final User user, final UserRequestDTO userRequest) {
        user.setFullName(userRequest.fullName());
        user.setPhoneNumber(userRequest.phoneNumber());
    }

    @Override
    public void updatePassword(final User user, final UserRequestDTO userRequest) {
        user.setPassword(userRequest.password());
        System.out.println("Pedido concluído");
    }

    @Override
    public void updateImageUrl(final User user, final String imageUrl) {
        user.setImageUrl(imageUrl);
    }

}
