package com.poc.rabbit.producerservice.controllers;

import brave.baggage.BaggageField;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.rabbit.producerservice.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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
    private final FanoutExchange directFanoutExchange;
    private final AtomicInteger requestId = new AtomicInteger();
    private final ObjectMapper objectMapper;

    public DirectMessagingController(RabbitTemplate template,
                                     @Value("${directAsyncConsumer.routingKey}") String asyncRoutingKey,
                                     @Value("${directSyncConsumer.routingKey}") String syncRoutingKey,
                                     @Qualifier("direct.exchange.async") DirectExchange directAsyncExchange,
                                     @Qualifier("direct.exchange.sync") DirectExchange directSyncExchange,
                                     @Qualifier("fanout.exchange.direct") FanoutExchange directFanoutExchange,
                                     ObjectMapper objectMapper) {
        this.template = template;
        this.asyncRoutingKey = asyncRoutingKey;
        this.syncRoutingKey = syncRoutingKey;
        this.directAsyncExchange = directAsyncExchange;
        this.directSyncExchange = directSyncExchange;
        this.directFanoutExchange = directFanoutExchange;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/async-get-employee")
    public void queryAsyncEmployee() {
        configMdc();
        Employee employee = Employee.builder()
                .employeeId(userId().toString())
                .firstName("First Name")
                .lastName("Last Name")
                .phoneNumber("123123123")
                .build();
        log.info("Sending employee async");
        template.convertAndSend(directAsyncExchange.getName(), asyncRoutingKey, employee);
    }

    @GetMapping("/sync-get-employee")
    public Employee querySyncEmployee() throws JsonProcessingException {
        configMdc();
        Employee employee = Employee.builder()
                .employeeId(userId().toString())
                .build();
        log.info("Sending message to get employee sync");
        Employee receivedEmployeeInfo = template.convertSendAndReceiveAsType(directSyncExchange.getName(), syncRoutingKey, employee, ParameterizedTypeReference.forType(Employee.class));
        log.info("Employee received is " + objectMapper.writeValueAsString(receivedEmployeeInfo));
        return receivedEmployeeInfo;
    }

    @GetMapping("/fanout-get-employee")
    public void queryFanoutEmployee() {
        configMdc();
        Employee employee = Employee.builder()
                .employeeId(userId().toString())
                .firstName("Fanout Name")
                .lastName("Fanout Name")
                .phoneNumber("9876543210")
                .build();
        log.info("Sending message to get employee fanout");
        template.convertAndSend(directFanoutExchange.getName(), "", employee);
    }

    private void configMdc() {
        String requestIdValue = String.valueOf(requestId.incrementAndGet());
        BaggageField.create("requestId").updateValue(requestIdValue);
        MDC.put("requestId", requestIdValue);
    }

    private Integer userId() {
        return new Random().nextInt(1000000) + 1000000;
    }
}
