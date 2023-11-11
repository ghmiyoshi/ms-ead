package com.ead.course.dtos;

import java.util.UUID;

public record NotificationCommandDTO(String title, String message, UUID userId) {}
