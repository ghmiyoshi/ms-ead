package com.ead.course.configs.security;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationJwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      var jwt = getJwt(request);
      if (isNotBlank(jwt) && jwtProvider.validateJwtToken(jwt)) {
        var username = jwtProvider.getUserNameFromJwtToken(jwt);
        var roles = jwtProvider.getClaimNameJwt(jwt, "roles");
        var userDetails = UserDetailsImpl.build(username, roles);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
