package com.poc.rabbit.directconsumer.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String syncRoutingKey;
    private final String syncDirectExchangeName;
    private final String syncDirectQueueName;

    private final String asyncRoutingKey;
    private final String asyncDirectExchangeName;
    private final String asyncDirectQueueName;

    public RabbitConfig(@Value("${directAsyncConsumer.routingKey}") String asyncRoutingKey,
                        @Value("${directAsyncConsumer.exchangeName}") String asyncDirectExchangeName,
                        @Value("${directAsyncConsumer.queueName}") String asyncDirectQueueName,
                        @Value("${directSyncConsumer.routingKey}") String syncRoutingKey,
                        @Value("${directSyncConsumer.exchangeName}") String syncDirectExchangeName,
                        @Value("${directSyncConsumer.queueName}") String syncDirectQueueName) {
        this.asyncRoutingKey = asyncRoutingKey;
        this.asyncDirectExchangeName = asyncDirectExchangeName;
        this.asyncDirectQueueName = asyncDirectQueueName;

        this.syncRoutingKey = syncRoutingKey;
        this.syncDirectExchangeName = syncDirectExchangeName;
        this.syncDirectQueueName = syncDirectQueueName;
    }

    @Bean
    @Qualifier("direct.exchange.async")
    public DirectExchange queryDirectAsyncExchange() {
        return new DirectExchange(asyncDirectExchangeName);
    }

    @Bean
    @Qualifier("direct.async.queue")
    public Queue queryDirectAsyncQueue() {
        return new Queue(asyncDirectQueueName);
    }

    @Bean
    @Qualifier("direct.exchange.async.binding")
    public Binding queryDirectExchangeAsyncBinding(
            @Qualifier("direct.exchange.async") DirectExchange exchange,
            @Qualifier("direct.async.queue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(asyncRoutingKey);
    }

    @Bean
    @Qualifier("direct.exchange.sync")
    public DirectExchange queryDirectSyncExchange() {
        return new DirectExchange(syncDirectExchangeName);
    }

    @Bean
    @Qualifier("direct.sync.queue")
    public Queue queryDirectSyncQueue() {
        return new Queue(syncDirectQueueName);
    }

    @Bean
    @Qualifier("direct.exchange.sync.binding")
    public Binding queryDirectExchangeSyncBinding(
            @Qualifier("direct.exchange.sync") DirectExchange exchange,
            @Qualifier("direct.sync.queue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(syncRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
