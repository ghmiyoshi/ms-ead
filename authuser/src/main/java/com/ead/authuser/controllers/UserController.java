package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.dtos.UserResponseDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specs.UserFilter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.ead.authuser.specs.UserSpecificationBuilder.toSpec;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping
    @JsonView(UserResponseDTO.Response.UserGet.class)
    public Page<UserResponseDTO> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "userId",
            direction = Sort.Direction.ASC) final Pageable pageable,
                                             @RequestParam(required = false) final UUID courseId) {
        final var userFilter = UserFilter.createFilter(null, null, null, courseId);
        return userRepository.findAll(toSpec(userFilter), pageable).map(UserResponseDTO::from);
    }

    @GetMapping("/{userId}")
    @JsonView(UserResponseDTO.Response.UserGet.class)
    public UserResponseDTO getOneUser(@PathVariable final UUID userId) {
        return UserResponseDTO.from(userService.findById(userId));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final UUID userId) {
        log.info("{}::deleteUser - user id received: {}", getClass().getSimpleName(), userId);
        userService.deleteById(userId);
        log.info("{}::deleteUser - deleted user id {}", getClass().getSimpleName(), userId);
    }

    @PutMapping("/{userId}")
    @JsonView(UserResponseDTO.Response.RegistrationPost.class)
    public UserResponseDTO updateUser(@PathVariable final UUID userId,
                                      @RequestBody @Validated(UserRequestDTO.Request.UserPut.class)
                                      @JsonView(UserRequestDTO.Request.UserPut.class) final UserRequestDTO userRequest) {
        log.info("{}::updateUser - received: {}", getClass().getSimpleName(), userRequest);
        var user = userService.findById(userId);
        userService.updateFullNameAndPhoneNumber(user, userRequest);
        user = userService.save(user);
        log.info("{}::updateUser - saved: {}", getClass().getSimpleName(), user);
        return UserResponseDTO.from(user);
    }

    @PutMapping("/{userId}/password")
    public String updatePassword(@PathVariable final UUID userId,
                                 @RequestBody @Validated(UserRequestDTO.Request.PasswordPut.class)
                                 @JsonView(UserRequestDTO.Request.PasswordPut.class) final UserRequestDTO userRequest) {
        var user = userService.findById(userId);
        userService.updatePassword(user, userRequest);
        userService.save(user);
        return "Password updated successfully";
    }

    @PutMapping("/{userId}/image")
    public String updateImage(@PathVariable final UUID userId,
                              @RequestBody @Validated(UserRequestDTO.Request.ImagePut.class)
                              @JsonView(UserRequestDTO.Request.ImagePut.class) final UserRequestDTO userRequest) {
        var user = userService.findById(userId);
        userService.updateImageUrl(user, userRequest.imageUrl());
        userService.save(user);
        return "Image url updated successfully";
    }

    @GetMapping("/specs")
    @JsonView(UserResponseDTO.Response.UserGet.class)
    public List<UserResponseDTO> getUserSpecs(@RequestParam(required = false) final UserType userType,
                                              @RequestParam(required = false) final UserStatus userStatus,
                                              @RequestParam(required = false) final String email,
                                              @RequestParam(required = false) final UUID courseId) {
        final var userFilter = UserFilter.createFilter(userType, userStatus, email, courseId);
        return userRepository.findAll(toSpec(userFilter))
                .stream().map(UserResponseDTO::from).toList();
    }

}
