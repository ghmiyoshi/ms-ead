package com.ead.authuser.dtos;

import com.ead.authuser.models.User;
import com.ead.authuser.models.enums.UserStatus;
import com.ead.authuser.models.enums.UserType;
import com.fasterxml.jackson.annotation.JsonView;

public record UserResponseDTO(
        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        String username,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        String email,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        String fullName,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        UserStatus userStatus,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        UserType userType,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        String phoneNumber,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class})
        String cpf,

        @JsonView({Response.RegistrationPost.class, Response.UserGet.class, Response.ImagePut.class})
        String imageUrl) {

    public interface Response {
        static interface RegistrationPost {}

        static interface UserGet {}

        static interface ImagePut {}
    }

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getFullName(),
                                   user.getUserStatus(), user.getUserType(),
                                   user.getPhoneNumber(), user.getCpf(), user.getImageUrl());
    }

}
