package com.ead.authuser.dtos;

import com.ead.authuser.utils.ObjectMapperUtils;
import com.ead.authuser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
    @NotBlank(groups = Request.RegistrationPost.class)
    @UsernameConstraint(groups = Request.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = Request.RegistrationPost.class)
    @JsonView(Request.RegistrationPost.class)
    String username,

    @Email(groups = Request.RegistrationPost.class)
    @NotBlank(groups = Request.RegistrationPost.class)
    @JsonView(Request.RegistrationPost.class)
    String email,

    @NotBlank(groups = {Request.RegistrationPost.class, Request.PasswordPut.class})
    @Size(min = 6, max = 20, groups = {Request.RegistrationPost.class, Request.PasswordPut.class})
    @JsonView({Request.RegistrationPost.class, Request.PasswordPut.class})
    String password,

    @NotBlank(groups = Request.PasswordPut.class)
    @Size(min = 6, max = 20, groups = Request.PasswordPut.class)
    @JsonView(Request.PasswordPut.class)
    String oldPassword,

    @JsonView({Request.RegistrationPost.class, Request.UserPut.class})
    String fullName,

    @JsonView({Request.RegistrationPost.class, Request.UserPut.class})
    String phoneNumber,

    @NotBlank(groups = Request.RegistrationPost.class)
    //@CPF(groups = Request.RegistrationPost.class)
    @JsonView(Request.RegistrationPost.class)
    String cpf,

    @NotBlank(groups = Request.ImagePut.class)
    @JsonView(Request.ImagePut.class)
    String imageUrl) {

  public interface Request {

    interface RegistrationPost {

    }

    interface UserPut {

    }

    interface PasswordPut {

    }

    interface ImagePut {

    }
  }

  @Override
  public String toString() {
    return ObjectMapperUtils.writeObjectInJson(this);
  }
}
