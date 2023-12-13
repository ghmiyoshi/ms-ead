package com.ead.authuser.models;

import com.ead.authuser.models.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_roles")
public class Role extends JsonAbstract implements GrantedAuthority {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID roleId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true, length = 30)
  private RoleType name;

  @Override
  public String getAuthority() {
    return this.name.name();
  }
}
