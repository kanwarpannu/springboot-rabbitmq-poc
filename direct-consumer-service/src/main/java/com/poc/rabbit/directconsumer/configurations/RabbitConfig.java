package com.poc.rabbit.directconsumer.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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

    private final String directFanoutQueue;
    private final String directFanoutExchange;

    private final String fanoutIndirectExchangeName;

    public RabbitConfig(@Value("${directAsyncConsumer.routingKey}") String asyncRoutingKey,
                        @Value("${directAsyncConsumer.exchangeName}") String asyncDirectExchangeName,
                        @Value("${directAsyncConsumer.queueName}") String asyncDirectQueueName,
                        @Value("${directSyncConsumer.routingKey}") String syncRoutingKey,
                        @Value("${directSyncConsumer.exchangeName}") String syncDirectExchangeName,
                        @Value("${directSyncConsumer.queueName}") String syncDirectQueueName,
                        @Value("${directFanoutProducer.queueName}") String directFanoutQueue,
                        @Value("${directFanoutProducer.exchangeName}") String directFanoutExchange,
                        @Value("${indirectFanoutProducer.exchangeName}") String fanoutIndirectExchangeName) {
        this.asyncRoutingKey = asyncRoutingKey;
        this.asyncDirectExchangeName = asyncDirectExchangeName;
        this.asyncDirectQueueName = asyncDirectQueueName;

        this.syncRoutingKey = syncRoutingKey;
        this.syncDirectExchangeName = syncDirectExchangeName;
        this.syncDirectQueueName = syncDirectQueueName;

        this.directFanoutQueue = directFanoutQueue;
        this.directFanoutExchange = directFanoutExchange;

        this.fanoutIndirectExchangeName = fanoutIndirectExchangeName;
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
    @Qualifier("fanout.exchange.direct")
    public FanoutExchange queryDirectFanoutExchange() {
        return new FanoutExchange(directFanoutExchange);
    }

    @Bean
    @Qualifier("fanout.exchange.direct.queue")
    public Queue queryDirectFanoutQueue() {
        return new Queue(directFanoutQueue);
    }

    @Bean
    @Qualifier("fanout.exchange.direct.binding")
    public Binding queryDirectFanoutBinding(
            @Qualifier("fanout.exchange.direct") FanoutExchange exchange,
            @Qualifier("fanout.exchange.direct.queue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    @Qualifier("fanout.exchange.indirect")
    public FanoutExchange queryIndirectFanoutExchange() {
        return new FanoutExchange(fanoutIndirectExchangeName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
