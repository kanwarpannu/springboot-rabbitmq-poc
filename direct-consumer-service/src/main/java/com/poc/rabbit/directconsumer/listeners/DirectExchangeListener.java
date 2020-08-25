package com.poc.rabbit.directconsumer.listeners;

import com.poc.rabbit.directconsumer.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DirectExchangeListener {

    @RabbitListener(queues = "#{queryDirectAsyncQueue.getName()}")
    public void handleAsyncMessage(Employee payload) {
        log.info("Request received: " + payload);
    }

    @RabbitListener(queues = "#{queryDirectSyncQueue.getName()}")
    public Employee handleSyncMessage(Employee payload) {
        log.info("Sync Request received");

        Employee employee = Employee.builder()
                .employeeId(payload.getEmployeeId())
                .firstName("Sync")
                .lastName("Message")
                .phoneNumber("1234")
                .build();

        log.info("Sending employee: " + employee);
        return employee;
    }
}
