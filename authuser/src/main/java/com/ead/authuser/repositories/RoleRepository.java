package com.ead.authuser.repositories;

import com.ead.authuser.models.Role;
import com.ead.authuser.models.enums.RoleType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> {

  Optional<Role> findByName(RoleType name);

}
