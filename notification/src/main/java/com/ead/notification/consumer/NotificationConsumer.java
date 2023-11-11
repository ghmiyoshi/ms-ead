package com.ead.notification.consumer;

import com.ead.notification.dto.NotificationCommandDTO;
import com.ead.notification.model.Notification;
import com.ead.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.ead.notification.model.NotificationStatusEnum.CREATED;

@Slf4j
@Component
public class NotificationConsumer {

    final NotificationService notificationService;

    public NotificationConsumer(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type =
                    ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ead.broker.key.notificationCommandKey}")
    )
    public void listenNotificationCommand(@Payload final NotificationCommandDTO notificationCommand) {
        log.info("{}::listen - Start process message: {}", getClass().getSimpleName(), notificationCommand);
        var notificationModel = new Notification();
        BeanUtils.copyProperties(notificationCommand, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(CREATED);
        notificationService.saveNotification(notificationModel);
        log.info("{}::listen - Finish process message: {}", getClass().getSimpleName(), notificationModel);
    }

}
