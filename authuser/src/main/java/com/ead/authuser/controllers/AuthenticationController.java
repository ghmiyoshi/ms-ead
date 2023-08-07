package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.dtos.UserResponseDTO;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    @PostMapping("/signup")
    @JsonView(UserResponseDTO.Response.RegistrationPost.class)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUSer(@RequestBody @Validated(UserRequestDTO.Request.RegistrationPost.class)
                                        @JsonView(UserRequestDTO.Request.RegistrationPost.class) final UserRequestDTO userRequest) {
        log.debug("{}::registerUSer - received: {}", getClass().getSimpleName(), userRequest);
        userService.validateUser(userRequest);
        var user = userService.newStudent();
        BeanUtils.copyProperties(userRequest, user);
        userService.save(user);
        log.debug("{}::registerUSer - saved: {}", getClass().getSimpleName(), user);
        return UserResponseDTO.from(user);
    }

}
