package com.ead.notification.configs.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private String username;
  private Collection<? extends GrantedAuthority> authorities;

  public static UserDetailsImpl build(final String username, final String roles) {
    List<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    return new UserDetailsImpl(username, authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean isAdmin() {
    return this.authorities.stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
  }
}
