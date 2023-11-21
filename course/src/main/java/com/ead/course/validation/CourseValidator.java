package com.ead.course.validation;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.infra.ValidationException;
import com.ead.course.models.Course_;
import com.ead.course.services.UserService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class CourseValidator implements Validator {

  private final UserService userService;
  @Qualifier("defaultValidator")
  private Validator validator;

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @Override
  public void validate(Object target, Errors errors) {
    CourseDto courseDto = (CourseDto) target;
    validator.validate(courseDto, errors);
    if (errors.hasErrors()) {
      throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
    }
    validateUserInstructor(courseDto.userInstructor(), errors);
  }

  private void validateUserInstructor(final UUID userInstructor, final Errors errors) {
    try {
      var user = userService.findById(userInstructor);
      if (UserType.STUDENT.equals(user.getUserType())) {
        errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError",
            "User must be INSTUCTOR or ADMIN");
        throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
      }
    } catch (Exception e) {
      errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError", "Instructor not found");
      throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
    }
  }

}
