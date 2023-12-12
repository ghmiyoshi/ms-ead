package com.ead.authuser.validation;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return !(isEmpty(username) || username.trim().isEmpty() || username.contains(" "));
  }

}
