package com.poc.rabbit.producerservice.controllers;

import com.poc.rabbit.producerservice.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class DirectMessagingController {

    private final String asyncRoutingKey;
    private final String syncRoutingKey;
    private final RabbitTemplate template;
    private final DirectExchange directAsyncExchange;
    private final DirectExchange directSyncExchange;
    public AtomicInteger requestId = new AtomicInteger();

    public DirectMessagingController(RabbitTemplate template,
                                     @Value("${directAsyncConsumer.routingKey}") String asyncRoutingKey,
                                     @Value("${directSyncConsumer.routingKey}") String syncRoutingKey,
                                     @Qualifier("direct.exchange.async") DirectExchange directAsyncExchange,
                                     @Qualifier("direct.exchange.sync") DirectExchange directSyncExchange) {
        this.template = template;
        this.asyncRoutingKey = asyncRoutingKey;
        this.syncRoutingKey = syncRoutingKey;
        this.directAsyncExchange = directAsyncExchange;
        this.directSyncExchange = directSyncExchange;
    }

    @GetMapping("/async-get-employee")
    public void queryAsyncEmployee() {
        Employee employee = Employee.builder()
                .employeeId(userId().toString())
                .firstName("First Name")
                .lastName("Last Name")
                .phoneNumber("123123123")
                .build();
        log.info("Sending message to get employee async");
        template.convertAndSend(directAsyncExchange.getName(), asyncRoutingKey, employee);
    }

    @GetMapping("/sync-get-employee")
    public Employee querySyncEmployee() {
        MDC.put("requestId", String.valueOf(requestId.incrementAndGet()));
        Employee employee = Employee.builder()
                .employeeId(userId().toString())
                .build();
        log.info("Sending message to get employee sync");
        return template.convertSendAndReceiveAsType(directSyncExchange.getName(), syncRoutingKey, employee, ParameterizedTypeReference.forType(Employee.class));
    }

    private Integer userId() {
        return new Random().nextInt(1000000) + 1000000;
    }
}
