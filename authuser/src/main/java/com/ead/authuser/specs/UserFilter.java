package com.ead.authuser.specs;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserFilter(UserType userType, UserStatus userStatus, String email, UUID courseId) {

    public static UserFilter createFilter(final UserType userType,
                                          final UserStatus userStatus,
                                          final String email,
                                          final UUID courseId) {
        return UserFilter.builder().userType(userType).userStatus(userStatus).email(email).courseId(courseId).build();
    }

}
