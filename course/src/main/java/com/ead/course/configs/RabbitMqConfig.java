package com.ead.course.configs;

import com.ead.course.utils.ObjectMapperUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${ead.broker.exchange.userEventExchange}")
  private String exchange;

  @Value("${ead.broker.queue.userEventQueue.name}")
  private String queue;

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

  @Bean
  public Queue filaEadCourse() {
    return QueueBuilder
            .durable(queue)
            .deadLetterExchange(exchange + ".dlx")
            .build();
  }

  @Bean
  public Queue filaDlqEadCourse() {
    return QueueBuilder
            .durable(queue + "-dlq")
            .build();
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return ExchangeBuilder
            .fanoutExchange(exchange)
            .build();
  }

  @Bean
  public FanoutExchange deadLetterExchange() {
    return ExchangeBuilder
            .fanoutExchange(exchange + ".dlx")
            .build();
  }

  @Bean
  public Binding bindEadCourse() {
    return BindingBuilder
            .bind(filaEadCourse())
            .to(fanoutExchange());
  }

  @Bean
  public Binding bindDlxEadCourse() {
    return BindingBuilder
            .bind(filaDlqEadCourse())
            .to(deadLetterExchange());
  }
}
