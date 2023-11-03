package com.ead.course.validation;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.enums.UserType;
import com.ead.course.infra.ValidationException;
import com.ead.course.models.Course_;
import com.ead.course.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@AllArgsConstructor
@Component
public class CourseValidator implements Validator {

    @Qualifier("defaultValidator")
    private Validator validator;

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDTO courseDTO = (CourseDTO) target;
        validator.validate(courseDTO, errors);
        if (errors.hasErrors()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
        }
        validateUserInstructor(courseDTO.userInstructor(), errors);
    }

    private void validateUserInstructor(final UUID userInstructor, final Errors errors) {
        try {
            var user = userService.findById(userInstructor);
            if (UserType.STUDENT.equals(user.getUserType())) {
                errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError", "User must be INSTUCTOR or ADMIN");
                throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
            }
        } catch (Exception e) {
            errors.rejectValue(Course_.USER_INSTRUCTOR, "UserInstructorError", "Instructor not found");
            throw new ValidationException(HttpStatus.BAD_REQUEST, errors.getAllErrors());
        }
    }

}
