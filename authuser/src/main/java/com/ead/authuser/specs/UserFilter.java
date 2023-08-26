package com.ead.authuser.specs;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import lombok.Builder;

@Builder
public record UserFilter(UserType userType, UserStatus userStatus, String email) {

    public static UserFilter createFilter(final UserType userType,
                                          final UserStatus userStatus,
                                          final String email) {
        return UserFilter.builder().userType(userType).userStatus(userStatus).email(email).build();
    }

}
