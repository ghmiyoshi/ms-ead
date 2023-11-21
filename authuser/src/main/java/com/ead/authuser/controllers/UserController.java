package com.ead.authuser.controllers;

import static com.ead.authuser.specs.UserSpecificationBuilder.toSpec;

import com.ead.authuser.dtos.UserRequestDto;
import com.ead.authuser.dtos.UserResponseDto;
import com.ead.authuser.models.enums.UserStatus;
import com.ead.authuser.models.enums.UserType;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specs.UserFilter;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

  private UserService userService;
  private UserRepository userRepository;

  @GetMapping
  @JsonView(UserResponseDto.Response.UserGet.class)
  public Page<UserResponseDto> getAllUsers(
      @PageableDefault(page = 0, size = 10, sort = "userId", direction =
          Sort.Direction.ASC) final Pageable pageable,
      @RequestParam(required = false) UserType userType,
      @RequestParam(required = false) UserStatus userStatus,
      @RequestParam(required = false) String email) {
    final var userFilter = UserFilter.createFilter(userType, userStatus, email);
    return userRepository.findAll(toSpec(userFilter), pageable).map(UserResponseDto::from);
  }

  @GetMapping("/{userId}")
  @JsonView(UserResponseDto.Response.UserGet.class)
  public UserResponseDto getOneUser(@PathVariable final UUID userId) {
    return UserResponseDto.from(userService.findById(userId));
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable final UUID userId) {
    log.info("{}::deleteUser - user id received: {}", getClass().getSimpleName(), userId);
    userService.deleteUser(userId);
    log.info("{}::deleteUser - deleted user id {}", getClass().getSimpleName(), userId);
  }

  @PutMapping("/{userId}")
  @JsonView(UserResponseDto.Response.RegistrationPost.class)
  public UserResponseDto updateUser(@PathVariable final UUID userId,
      @RequestBody @Validated(UserRequestDto.Request.UserPut.class)
      @JsonView(UserRequestDto.Request.UserPut.class) final UserRequestDto userRequest) {
    log.info("{}::updateUser - received: {}", getClass().getSimpleName(), userRequest);
    var user = userService.findById(userId);
    userService.updateFullNameAndPhoneNumber(user, userRequest);
    user = userService.saveUser(user);
    log.info("{}::updateUser - saved: {}", getClass().getSimpleName(), user);
    return UserResponseDto.from(user);
  }

  @PutMapping("/{userId}/password")
  public String updatePassword(@PathVariable final UUID userId,
      @RequestBody @Validated(UserRequestDto.Request.PasswordPut.class)
      @JsonView(UserRequestDto.Request.PasswordPut.class) final UserRequestDto userRequest) {
    var user = userService.findById(userId);
    userService.updatePassword(user, userRequest);
    userService.saveUser(user);
    return "Password updated successfully";
  }

  @PutMapping("/{userId}/image")
  public String updateImage(@PathVariable final UUID userId,
      @RequestBody @Validated(UserRequestDto.Request.ImagePut.class)
      @JsonView(UserRequestDto.Request.ImagePut.class) final UserRequestDto userRequest) {
    var user = userService.findById(userId);
    userService.updateImageUrl(user, userRequest.imageUrl());
    userService.updatePasswordUser(user);
    return "Image url updated successfully";
  }

  @GetMapping("/specs")
  @JsonView(UserResponseDto.Response.UserGet.class)
  public List<UserResponseDto> getUserSpecs(@RequestParam(required = false) final UserType userType,
      @RequestParam(required = false) final UserStatus userStatus,
      @RequestParam(required = false) final String email) {
    final var userFilter = UserFilter.createFilter(userType, userStatus, email);
    return userRepository.findAll(toSpec(userFilter))
        .stream().map(UserResponseDto::from).toList();
  }
}
