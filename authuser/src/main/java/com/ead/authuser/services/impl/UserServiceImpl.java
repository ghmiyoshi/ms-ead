package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserEventDTO;
import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.models.enums.RoleType;
import com.ead.authuser.models.enums.UserType;
import com.ead.authuser.publishers.UserEventPubliser;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static com.ead.authuser.models.enums.ActionType.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserEventPubliser userEventPubliser;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Cacheable
    @Override
    public Page<User> findAll(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(final UUID userId) {
        log.info("{}::findById - user id: {}", getClass().getSimpleName(), userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public void deleteById(final UUID userId) {
        var user = findById(userId);
        userRepository.delete(user);
        log.info("{}::deleteById - user id: {}", getClass().getSimpleName(), userId);
    }

    @Override
    public User save(final User user) {
        log.info("{}::save - user: {}", getClass().getSimpleName(), user);
        return userRepository.save(user);
    }

    @Override
    public void validateUser(final UserRequestDTO userRequest) {
        if (userRepository.existsUserByUsernameOrEmail(userRequest.username(), userRequest.email())) {
            log.error("{}::validateUser - User is already taken", getClass().getSimpleName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already taken");
        }
    }

    @Override
    public User newStudent(UserRequestDTO userRequest) {
        var user = new User();
        user.setUserType(UserType.STUDENT);
        user.getRoles().add(roleService.findByRoleName(RoleType.STUDENT));
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        BeanUtils.copyProperties(userRequest, user, "password");
        return user;
    }

    @Override
    public void subscriptionInstructor(final User user) {
        log.info("{}::subscriptionInstructor - Subscription user for instructor: {}",
                 getClass().getSimpleName(), user);
        user.setUserType(UserType.INSTRUCTOR);
    }

    @Override
    public void updateFullNameAndPhoneNumber(final User user, final UserRequestDTO userRequest) {
        log.info("{}::updateFullNameAndPhoneNumber - Update full name and phone number",
                 getClass().getSimpleName());
        user.setFullName(userRequest.fullName());
        user.setPhoneNumber(userRequest.phoneNumber());
    }

    @Override
    public void updatePassword(final User user, final UserRequestDTO userRequest) {
        log.info("{}::updatePassword - Update password", getClass().getSimpleName());
        user.setPassword(userRequest.password());
    }

    @Override
    public void updateImageUrl(final User user, final String imageUrl) {
        log.info("{}::updateImageUrl - Update image url", getClass().getSimpleName());
        user.setImageUrl(imageUrl);
    }

    @Transactional
    @Override
    public User saveUser(final User user) {
        var userSaved = save(user);
        userEventPubliser.publishUserEvent(UserEventDTO.from(userSaved, CREATE));
        log.info("{}::saveUser - Send message for queue: {}", getClass().getSimpleName(), userSaved);
        return userSaved;
    }

    @Override
    public void deleteUser(UUID userId) {
        deleteById(userId);
        var userEvent = new UserEventDTO();
        userEvent.setUserId(userId);
        userEvent.setActionType(DELETE.name());
        userEventPubliser.publishUserEvent(userEvent);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        var userSaved = save(user);
        userEventPubliser.publishUserEvent(UserEventDTO.from(userSaved, UPDATE));
        log.info("{}::updateUser - Send message for queue: {}", getClass().getSimpleName(), userSaved);
        return userSaved;
    }

    @Override
    public User updatePasswordUser(User user) {
        return save(user);
    }

}
