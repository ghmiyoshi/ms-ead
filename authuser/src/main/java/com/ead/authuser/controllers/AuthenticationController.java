package com.ead.authuser.controllers;

import com.ead.authuser.dtos.JwtDto;
import com.ead.authuser.dtos.LoginDto;
import com.ead.authuser.dtos.UserRequestDto;
import com.ead.authuser.dtos.UserResponseDto;
import com.ead.authuser.models.UserDetailsImpl;
import com.ead.authuser.services.UserService;
import com.ead.authuser.utils.JwtProvider;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
  @JsonView(UserResponseDto.Response.RegistrationPost.class)
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto registerUser(
      @RequestBody @Validated(UserRequestDto.Request.RegistrationPost.class)
      @JsonView(UserRequestDto.Request.RegistrationPost.class) final UserRequestDto userRequest) {
    log.debug("[method:registerUser] userRequest: {}", userRequest);
    userService.validateUser(userRequest);
    var user = userService.newStudent(userRequest);
    userService.saveUser(user);
    log.debug("[method:registerUser] saved: {}", user);
    return UserResponseDto.from(user);
  }

  @PostMapping("/login")
  public JwtDto authenticationUser(@Valid @RequestBody LoginDto loginDto) {
    final var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final var jwt = jwtProvider.generateToken((UserDetailsImpl) authentication.getPrincipal());
    return new JwtDto(jwt, "Bearer");
  }
}
