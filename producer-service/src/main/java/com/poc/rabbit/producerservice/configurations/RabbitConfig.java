package com.poc.rabbit.producerservice.configurations;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String syncDirectExchangeName;
    private final String asyncDirectExchangeName;

    public RabbitConfig(@Value("${directAsyncConsumer.exchangeName}") String asyncDirectExchangeName,
                        @Value("${directSyncConsumer.exchangeName}") String syncDirectExchangeName) {
        this.asyncDirectExchangeName = asyncDirectExchangeName;
        this.syncDirectExchangeName = syncDirectExchangeName;
    }

    @Bean
    @Qualifier("direct.exchange.async")
    public DirectExchange queryDirectAsyncExchange() {
        return new DirectExchange(asyncDirectExchangeName);
    }

    @Bean
    @Qualifier("direct.exchange.sync")
    public DirectExchange queryDirectSyncExchange() {
        return new DirectExchange(syncDirectExchangeName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
