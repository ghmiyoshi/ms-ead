package com.ead.authuser.services.impl;

import com.ead.authuser.models.Role;
import com.ead.authuser.models.enums.RoleType;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.services.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  @Override
  public Role findByRoleName(RoleType name) {
    return roleRepository.findByName(name).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
  }
}
