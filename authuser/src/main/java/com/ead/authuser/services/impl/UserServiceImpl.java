package com.ead.authuser.services.impl;

import static com.ead.authuser.models.enums.ActionType.CREATE;
import static com.ead.authuser.models.enums.ActionType.DELETE;
import static com.ead.authuser.models.enums.ActionType.UPDATE;
import static com.ead.authuser.models.enums.RoleType.ROLE_INSTRUCTOR;
import static com.ead.authuser.models.enums.RoleType.ROLE_STUDENT;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.dtos.UserRequestDto;
import com.ead.authuser.models.User;
import com.ead.authuser.models.enums.UserType;
import com.ead.authuser.publishers.UserEventPubliser;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.UserService;
import java.util.UUID;
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
    log.info("[method:findAll] Find all users");
    return userRepository.findAll(pageable);
  }

  @Override
  public User findById(final UUID userId) {
    log.info("[method:findById] userId: {}", userId);
    return userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  @Override
  public void deleteById(final UUID userId) {
    var user = findById(userId);
    userRepository.delete(user);
    log.info("[method:deleteById] userId: {}", userId);
  }

  @Override
  public User save(final User user) {
    log.info("[method:save] user: {}", user);
    return userRepository.save(user);
  }

  @Override
  public void validateUser(final UserRequestDto userRequest) {
    if (userRepository.existsUserByUsernameOrEmailOrCpf(userRequest.username(),
            userRequest.email(), userRequest.cpf())) {
      log.error("[method:validateUser] User is already taken");
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already taken");
    }
  }

  @Override
  public User newStudent(UserRequestDto userRequest) {
    var user = new User();
    user.setUserType(UserType.STUDENT);
    user.getRoles().add(roleService.findByRoleName(ROLE_STUDENT));
    user.setPassword(passwordEncoder.encode(userRequest.password()));
    BeanUtils.copyProperties(userRequest, user, "password");
    return user;
  }

  @Override
  public void subscriptionInstructor(final User user) {
    log.info("[method:subscriptionInstructor] Subscription user for instructor: {}", user);
    user.setUserType(UserType.INSTRUCTOR);
    var role = roleService.findByRoleName(ROLE_INSTRUCTOR);
    user.getRoles().add(role);
  }

  @Override
  public void updateFullNameAndPhoneNumber(final User user, final UserRequestDto userRequest) {
    log.info("[method:updateFullNameAndPhoneNumber] Update full name and phone number");
    user.setFullName(userRequest.fullName());
    user.setPhoneNumber(userRequest.phoneNumber());
  }

  @Override
  public void updatePassword(final User user, final UserRequestDto userRequest) {
    log.info("[method:updatePassword] Update password");
    user.setPassword(userRequest.password());
  }

  @Override
  public void updateImageUrl(final User user, final String imageUrl) {
    log.info("[method:updateImageUrl] Update image url");
    user.setImageUrl(imageUrl);
  }

  @Transactional
  @Override
  public User saveUser(final User user) {
    var userSaved = save(user);
    userEventPubliser.publishUserEvent(UserEventDto.from(userSaved, CREATE));
    log.info("[method:saveUser] Send message for queue: {}", userSaved);
    return userSaved;
  }

  @Override
  public void deleteUser(UUID userId) {
    deleteById(userId);
    var userEvent = new UserEventDto();
    userEvent.setUserId(userId);
    userEvent.setActionType(DELETE.name());
    userEventPubliser.publishUserEvent(userEvent);
  }

  @Transactional
  @Override
  public User updateUser(User user) {
    var userSaved = save(user);
    userEventPubliser.publishUserEvent(UserEventDto.from(userSaved, UPDATE));
    log.info("[method:updateUser] Send message for queue: {}", userSaved);
    return userSaved;
  }

  @Override
  public User updatePasswordUser(User user) {
    return save(user);
  }
}
