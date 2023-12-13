package com.ead.authuser.repositories;

import com.ead.authuser.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

  boolean existsUserByUsernameOrEmail(String username, String email);

  @EntityGraph(attributePaths = "roles")
  Optional<User> findByUsername(String username);

  @EntityGraph(attributePaths = "roles")
  Optional<User> findByUserId(UUID userId);
}
