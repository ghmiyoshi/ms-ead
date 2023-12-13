package com.ead.course.async.consumers;

import com.ead.course.dtos.UserEventDto;
import com.ead.course.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserConsumer {

  private UserService userService;

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
      exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT,
          ignoreDeclarationExceptions = "true")))
  public void listenUserEvent(@Payload UserEventDto userEventDto) {
    log.info("{}::listenUserEvent - Start process message: {}", getClass().getSimpleName(),
        userEventDto);
    userEventDto.getActionType().execute(userService, userEventDto);
    log.info("{}::listenUserEvent - Finish process message", getClass().getSimpleName());
  }
}
