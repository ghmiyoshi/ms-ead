package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record LessonDto(
    @NotBlank
    String title,
    @NotBlank
    String description,
    @NotBlank
    String videoUrl
) {

}
