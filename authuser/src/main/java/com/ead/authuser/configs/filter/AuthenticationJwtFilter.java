package com.ead.authuser.configs.filter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.ead.authuser.services.impl.UserDetailsServiceImpl;
import com.ead.authuser.utils.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
      if (isNotBlank(jwt) && jwtProvider.validateJwtToken(jwt)) {
        var userId = jwtProvider.getUserIdFromJwtToken(jwt);
        var userDetails = userDetailsService.loadUserByUserId(UUID.fromString(userId));
        var authentication = userDetailsService.getAuthentication(userDetails);
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("[method:doFilterInternal] Cannot set user authentication: {}", e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) {
    log.info("[method:getJwt] Getting jwt token from request");
    var authHeader = request.getHeader("Authorization");
    return isNotEmpty(authHeader) && authHeader.startsWith("Bearer ") ?
        authHeader.substring(7, authHeader.length()) : StringUtils.EMPTY;
  }
}
