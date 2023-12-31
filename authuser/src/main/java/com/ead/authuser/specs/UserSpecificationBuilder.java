package com.ead.authuser.specs;

import static java.util.Objects.nonNull;

import com.ead.authuser.models.User;
import com.ead.authuser.models.User_;
import com.ead.authuser.models.enums.UserStatus;
import com.ead.authuser.models.enums.UserType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecificationBuilder {

  public static Specification<User> toSpec(final UserFilter userFilter) {
    Specification<User> specification =
        Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

    if (nonNull(userFilter.userType())) {
      specification = specification.and(byUserType(userFilter.userType()));
    }

    if (nonNull(userFilter.userStatus())) {
      specification = specification.and(byUserStatus(userFilter.userStatus()));
    }

    if (nonNull(userFilter.email())) {
      specification = specification.and(byEmail(userFilter.email()));
    }

    return specification;
  }

  private static Specification<User> byUserType(final UserType userType) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_TYPE),
        userType);
  }

  private static Specification<User> byUserStatus(final UserStatus userStatus) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_STATUS),
        userStatus);
  }

  private static Specification<User> byEmail(final String email) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL),
        "%" + email + "%");
  }
}
