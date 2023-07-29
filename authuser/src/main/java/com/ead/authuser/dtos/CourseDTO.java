package com.ead.authuser.dtos;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;

import java.util.UUID;

public record CourseDTO(UUID courseId,
                        String name,
                        String description,
                        String imageUrl,
                        CourseStatus courseStatus,
                        UUID userInstructor,
                        CourseLevel courseLevel) {
}
