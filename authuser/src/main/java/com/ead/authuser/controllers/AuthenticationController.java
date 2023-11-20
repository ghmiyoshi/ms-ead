package com.ead.authuser.controllers;

import com.ead.authuser.configs.JwtProvider;
import com.ead.authuser.dtos.JwtDTO;
import com.ead.authuser.dtos.LoginDTO;
import com.ead.authuser.dtos.UserRequestDTO;
import com.ead.authuser.dtos.UserResponseDTO;
import com.ead.authuser.models.UserDetailsImpl;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    @JsonView(UserResponseDTO.Response.RegistrationPost.class)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUSer(@RequestBody @Validated(UserRequestDTO.Request.RegistrationPost.class)
                                        @JsonView(UserRequestDTO.Request.RegistrationPost.class) final UserRequestDTO userRequest) {
        log.debug("{}::registerUSer - received: {}", getClass().getSimpleName(), userRequest);
        userService.validateUser(userRequest);
        var user = userService.newStudent(userRequest);
        userService.saveUser(user);
        log.debug("{}::registerUSer - saved: {}", getClass().getSimpleName(), user);
        return UserResponseDTO.from(user);
    }

    @PostMapping("/login")
    public JwtDTO authenticationUser(@Valid @RequestBody LoginDTO loginDTO) {
        final var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = jwtProvider.generateToken((UserDetailsImpl) authentication.getPrincipal());
        return new JwtDTO(jwt, "Bearer");
    }
}
