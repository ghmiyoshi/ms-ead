package com.ead.course.async.publishers;

import com.ead.course.dtos.NotificationCommandDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

    @Value(value = "${ead.broker.key.notificationCommandKey}")
    private String notificationCommandKey;

    public void publishNotificationCommand(final NotificationCommandDTO notificationCommand) {
        log.info("{}::publishNotificationCommand - Publish notification message: {}",
                 getClass().getSimpleName(), notificationCommand);
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, notificationCommand);
    }

}
