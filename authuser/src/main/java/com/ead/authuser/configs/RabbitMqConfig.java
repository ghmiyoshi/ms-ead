package com.ead.authuser.configs;

import com.ead.authuser.utils.ObjectMapperUtils;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${ead.broker.exchange.userEvent}")
  private String exchangeUserEvent;

  @Bean
  public RabbitTemplate criaRabbitTemplate(final ConnectionFactory connectionFactory,
      final Jackson2JsonMessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter converteMensagem() {
    return new Jackson2JsonMessageConverter(ObjectMapperUtils.objectMapper());
  }

  @Bean
  public FanoutExchange fanoutUserEvent() {
    return new FanoutExchange(exchangeUserEvent);
  }
}
