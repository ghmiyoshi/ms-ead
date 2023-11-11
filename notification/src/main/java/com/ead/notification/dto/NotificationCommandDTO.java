package com.ead.notification.dto;

import java.util.UUID;

public record NotificationCommandDTO(String title, String message, UUID userId) {}
