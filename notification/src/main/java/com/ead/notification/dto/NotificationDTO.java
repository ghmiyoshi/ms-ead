package com.ead.notification.dto;

import com.ead.notification.model.NotificationStatusEnum;
import jakarta.validation.constraints.NotNull;


public record NotificationDTO(@NotNull NotificationStatusEnum notificationStatus) {}
