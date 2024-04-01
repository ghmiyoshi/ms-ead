package com.ead.authuser.services;

import com.ead.authuser.models.Role;
import com.ead.authuser.models.enums.RoleType;

public interface RoleService {

  Role findByRoleName(RoleType name);
}
