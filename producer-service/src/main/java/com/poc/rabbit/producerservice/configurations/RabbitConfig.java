package com.poc.rabbit.producerservice.configurations;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    @Qualifier("direct.exchange.async")
    public DirectExchange queryDirectAsyncExchange() {
        return new DirectExchange("direct.exchange.async");
    }

    @Bean
    @Qualifier("direct.exchange.sync")
    public DirectExchange queryDirectSyncExchange() {
        return new DirectExchange("direct.exchange.sync");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
