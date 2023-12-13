package com.ead.notification.repository;

import com.ead.notification.model.Notification;
import com.ead.notification.model.NotificationStatusEnum;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatusEnum notificationStatus,
                                                            Pageable pageable);

    Optional<Notification> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
