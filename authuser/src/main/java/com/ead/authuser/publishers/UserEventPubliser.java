package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPubliser {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(final UserEventDTO userEvent) {
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEvent);
    }

}
