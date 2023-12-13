package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserDetailsImpl;
import com.ead.authuser.repositories.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final var user = userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("User not found with username: " + username));
    return UserDetailsImpl.build(user);
  }

  public UserDetails loadUserByUserId(final UUID userId) throws UsernameNotFoundException {
    final var user = userRepository.findByUserId(userId).orElseThrow(
        () -> new UsernameNotFoundException("User not found"));
    return UserDetailsImpl.build(user);
  }

  public UsernamePasswordAuthenticationToken getAuthentication(UserDetails userDetails) {
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
