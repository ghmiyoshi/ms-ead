package com.ead.course.specs;

import lombok.Builder;

import java.util.UUID;

@Builder
public record LessonFilter(String title, UUID moduleId) {

    public static LessonFilter createFilter(final String title, final UUID moduleId) {
        return LessonFilter.builder().title(title).moduleId(moduleId).build();
    }

}
