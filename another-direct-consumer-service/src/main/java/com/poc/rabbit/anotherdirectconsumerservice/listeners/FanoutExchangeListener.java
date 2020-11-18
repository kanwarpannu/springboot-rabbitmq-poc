package com.poc.rabbit.anotherdirectconsumerservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.rabbit.anotherdirectconsumerservice.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutExchangeListener {
    private final ObjectMapper objectMapper;

    public FanoutExchangeListener(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "#{queryDirectFanoutQueue.getName()}")
    public void handleDirectFanoutMessage(Employee payload) throws JsonProcessingException {
        log.info("Request received: " + objectMapper.writeValueAsString(payload));
    }

    @RabbitListener(queues = "#{queryIndirectFanoutQueue.getName()}")
    public void handleIndirectFanoutMessage(Employee payload) throws JsonProcessingException {
        log.info("Request received: " + objectMapper.writeValueAsString(payload));
    }

}
