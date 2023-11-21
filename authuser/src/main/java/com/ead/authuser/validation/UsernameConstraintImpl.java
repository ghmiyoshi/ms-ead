package com.ead.authuser.validation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return isNull(username) || username.trim().isEmpty() || username.contains(" ");
  }

}
