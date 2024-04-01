package com.ead.course.models;

import com.ead.course.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_users")
public class User {

  @EqualsAndHashCode.Include
  @Id
  private UUID userId;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 150)
  private String fullName;

  @Column(nullable = false)
  private String userStatus;

  /* Nao foi usado enum pq nao fica acoplado com o ms de ead-user.
   Sempre que tiver um novo UserType ou UserStatus em ead-user
   aqui teria que atualizar tmb. */
  @Column(nullable = false)
  private String userType;

    @Column(length = 20, unique = true)
  private String cpf;

  @Column
  private String imageUrl;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(mappedBy = "users")
  private Set<Course> courses;

    public void isBlocked() {
        if (UserStatus.BLOCKED.name().equals(this.userStatus)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is blocked");
        }
    }
}
