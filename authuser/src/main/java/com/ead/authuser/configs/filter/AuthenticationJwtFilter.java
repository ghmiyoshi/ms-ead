package com.ead.authuser.configs.filter;

import com.ead.authuser.services.impl.UserDetailsServiceImpl;
import com.ead.authuser.utils.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationJwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      var jwt = getJwt(request);
      if (jwtProvider.validateJwtToken(jwt)) {
        var username = jwtProvider.getUserNameFromJwtToken(jwt);
        var userDetails = userDetailsService.loadUserByUsername(username);
        var authentication = userDetailsService.getAuthentication(userDetails);
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("[method:doFilterInternal] Cannot set user authentication: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) {
    log.info("[method:getJwt] Getting jwt token from request");
    var authHeader = request.getHeader("Authorization");
    if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7, authHeader.length());
    }
    log.error("[method:getJwt] Token is not present");
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is not present");
  }
}