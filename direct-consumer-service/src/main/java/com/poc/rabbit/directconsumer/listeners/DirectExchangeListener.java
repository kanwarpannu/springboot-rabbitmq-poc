package com.poc.rabbit.directconsumer.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.rabbit.directconsumer.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DirectExchangeListener {
    private final ObjectMapper objectMapper;

    public DirectExchangeListener(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "#{queryDirectAsyncQueue.getName()}")
    public void handleAsyncMessage(Employee payload) throws JsonProcessingException {
        log.info("Request received: " + objectMapper.writeValueAsString(payload));
    }

    @RabbitListener(queues = "#{queryDirectSyncQueue.getName()}")
    public Employee handleSyncMessage(Employee payload) throws JsonProcessingException {
        log.info("Sync Request received");

        Employee employee = Employee.builder()
                .employeeId(payload.getEmployeeId())
                .firstName("Sync")
                .lastName("Message")
                .phoneNumber("1234")
                .build();

        log.info("Sending employee: " + objectMapper.writeValueAsString(employee));
        return employee;
    }
}
