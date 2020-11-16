package com.poc.rabbit.anotherdirectconsumerservice.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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

    private final String directFanoutQueue;
    private final String directFanoutExchange;

    public RabbitConfig(@Value("${directFanoutProducer.queueName}") String directFanoutQueue,
                        @Value("${directFanoutProducer.exchangeName}") String directFanoutExchange) {
        this.directFanoutQueue = directFanoutQueue;
        this.directFanoutExchange = directFanoutExchange;
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
    public Binding queryDirectExchangeAsyncBinding(
            @Qualifier("fanout.exchange.direct") FanoutExchange exchange,
            @Qualifier("fanout.exchange.direct.queue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
