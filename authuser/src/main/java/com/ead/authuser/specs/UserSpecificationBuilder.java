package com.ead.authuser.specs;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.ead.authuser.models.UserCourse_;
import com.ead.authuser.models.User_;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Objects.nonNull;

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

        if (nonNull(userFilter.courseId())) {
            specification = specification.and(byUserCourseId(userFilter.courseId()));
        }

        return specification;
    }

    private static Specification<User> byUserType(final UserType userType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_TYPE), userType);
    }

    private static Specification<User> byUserStatus(final UserStatus userStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User_.USER_STATUS), userStatus);
    }

    private static Specification<User> byEmail(final String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(User_.EMAIL), "%" + email + "%");
    }

    private static Specification<User> byUserCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            Join<User, UserCourse> usersCourses = root.join(User_.USERS_COURSES);
            return criteriaBuilder.equal(usersCourses.get(UserCourse_.COURSE_ID), courseId);
        };
    }

}
