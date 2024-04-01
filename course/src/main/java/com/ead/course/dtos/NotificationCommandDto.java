package com.ead.course.dtos;

import com.ead.course.utils.ObjectMapperUtils;
import java.util.UUID;

public record NotificationCommandDto(String title, String message, UUID userId) {
    @Override
    public String toString() {
        return ObjectMapperUtils.writeObjectInJson(this);
    }
}
