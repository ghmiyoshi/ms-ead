package com.ead.course.validation;

import com.ead.course.configs.security.AuthenticationCurrentUserService;
import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.infra.ValidationException;
import com.ead.course.models.Course_;
import com.ead.course.services.UserService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@AllArgsConstructor
@Component
public class CourseValidator implements Validator {

  private final UserService userService;
  private final AuthenticationCurrentUserService authenticationCurrentUserService;

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    final var courseDto = (CourseDto) target;
    if (errors.hasErrors()) {
      throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
    }
    validateUserInstructor(courseDto.userInstructor(), errors);
  }

  private void validateUserInstructor(final UUID userInstructor, final Errors errors) {
    try {
      final var currentUser = authenticationCurrentUserService.getCurrentUser();
      if (userInstructor.equals(currentUser.getUserId()) || currentUser.isAdmin()) {
        var user = userService.findById(userInstructor);
        if (!UserType.INSTRUCTOR.name().equals(user.getUserType())) {
          throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
        }
      } else {
        throw new AccessDeniedException("Forbidden");
      }
    } catch (ValidationException e) {
      log.error("[method:validateUserInstructor] User must be INSTUCTOR or ADMIN");
      errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError",
              "User must be INSTUCTOR or ADMIN");
      throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
    } catch (AccessDeniedException e) {
      log.error("[method:validateUserInstructor] User instructor not equals current user");
      errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError",
          "User instructor not equals current user");
      throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
    }
  }
}
