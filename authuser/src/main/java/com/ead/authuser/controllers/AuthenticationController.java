package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.dtos.UserResponseDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        userService.validateUser(userRequest);
        var user = User.newStudent();
        BeanUtils.copyProperties(userRequest, user);
        return UserResponseDTO.from(userService.save(user));
    }

}
