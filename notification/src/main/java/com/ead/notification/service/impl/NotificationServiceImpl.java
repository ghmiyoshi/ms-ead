package com.ead.notification.service.impl;

import static com.ead.notification.model.NotificationStatusEnum.CREATED;

import com.ead.notification.model.Notification;
import com.ead.notification.repository.NotificationRepository;
import com.ead.notification.service.NotificationService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(final Notification notification) {
        log.info("{}::saveNotification - Save notification: {}", getClass().getSimpleName(), notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findAllNotificationsByUser(final UUID userId, final Pageable pageable) {
        log.info("{}::findAllNotificationsByUser - User id: {}", getClass().getSimpleName(), userId);
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, CREATED, pageable);
    }

    @Override
    public Notification findByNotificationIdAndUserId(final UUID notificationId, final UUID userId) {
        log.info("{}::findByNotificationIdAndUserId - Notification id: {} and user id: {}", getClass().getSimpleName(),
                 notificationId,
                 userId);
        return notificationRepository.findByNotificationIdAndUserId(notificationId, userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));
    }
}
