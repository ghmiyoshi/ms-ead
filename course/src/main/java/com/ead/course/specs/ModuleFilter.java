package com.ead.course.specs;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ModuleFilter(String title, UUID courseId) {

    public static ModuleFilter createFilter(final String title, final UUID courseId) {
        return ModuleFilter.builder().title(title).courseId(courseId).build();
    }

}
