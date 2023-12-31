package com.ead.course.configs;

import com.ead.course.utils.ObjectMapperUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Bean
  public RabbitTemplate criaRabbitTemplate(final ConnectionFactory connectionFactory,
      final Jackson2JsonMessageConverter messageConverter) {
    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter converteMensagem() {
    return new Jackson2JsonMessageConverter(ObjectMapperUtils.objectMapper());
  }
}
