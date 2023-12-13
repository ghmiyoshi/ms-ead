package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCurrentUserService {

  public UserDetailsImpl getUserDetailsService() {
    return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
  }
}
