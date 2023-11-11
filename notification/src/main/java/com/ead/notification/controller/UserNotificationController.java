package com.ead.notification.controller;

import com.ead.notification.dto.NotificationDTO;
import com.ead.notification.model.Notification;
import com.ead.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private NotificationService notificationService;


    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<Notification>> getAllNotificationsByUser(@PathVariable final UUID userId,
                                                                        @PageableDefault(page = 0, size = 10, sort =
                                                                                "notificationId", direction =
                                                                                Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findAllNotificationsByUser(userId,
                                                                                                        pageable));
    }

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
