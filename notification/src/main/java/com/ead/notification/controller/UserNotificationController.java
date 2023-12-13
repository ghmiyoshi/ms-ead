package com.ead.notification.controller;

import com.ead.notification.dto.NotificationDTO;
import com.ead.notification.model.Notification;
import com.ead.notification.service.NotificationService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private NotificationService notificationService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<Notification>> getAllNotificationsByUser(@PathVariable final UUID userId,
                                                                        @PageableDefault(page = 0, size = 10, sort =
                                                                                "notificationId", direction =
                                                                                Sort.Direction.ASC) final Pageable pageable,
                                                                        Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUser(userId,
                                                                                                        pageable));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable UUID userId,
                                                     @PathVariable UUID notificationId,
                                                     @RequestBody @Valid NotificationDTO notificationDTO) {
        Notification notification = notificationService
                .findByNotificationIdAndUserId(notificationId, userId);
        notification.setNotificationStatus(notificationDTO.notificationStatus());
        notificationService.saveNotification(notification);
        return ResponseEntity.status(HttpStatus.OK).body(notification);
    }
}
