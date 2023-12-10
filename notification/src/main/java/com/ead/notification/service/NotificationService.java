package com.ead.notification.service;

import com.ead.notification.model.Notification;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Notification saveNotification(Notification notificationModel);

    Page<Notification> findAllNotificationsByUser(UUID userId, Pageable pageable);

    Notification findByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
