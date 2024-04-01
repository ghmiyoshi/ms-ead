package com.ead.course.async.consumers;

import com.ead.course.dtos.UserEventDto;
import com.ead.course.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserConsumer {

  private UserService userService;

    @RabbitListener(queues = "${ead.broker.queue.userEventQueue.name}")
  public void listenUserEvent(@Payload UserEventDto userEventDto) {
        log.info("[method:listenUserEvent] Start process message: {}", userEventDto);
    userEventDto.getActionType().execute(userService, userEventDto);
        log.info("[method:listenUserEvent] Finish process message");
  }
}
