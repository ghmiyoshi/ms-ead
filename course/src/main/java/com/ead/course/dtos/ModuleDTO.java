package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuleDTO(
        @NotBlank
        String title,
        @NotBlank
        String description
) {}
