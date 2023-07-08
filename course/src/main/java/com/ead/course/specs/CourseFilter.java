package com.ead.course.specs;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import lombok.Builder;

@Builder
public record CourseFilter(CourseLevel courseLevel, CourseStatus courseStatus, String name) {

    public static CourseFilter createFilter(final CourseLevel courseLevel, final CourseStatus courseStatus,
                                            final String name) {
        return CourseFilter.builder().courseLevel(courseLevel).courseStatus(courseStatus).name(name).build();
    }

}
