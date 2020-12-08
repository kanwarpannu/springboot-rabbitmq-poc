package com.poc.rabbit.producerservice.contract;

import com.poc.rabbit.producerservice.controllers.DirectMessagingController;
import org.mockito.InjectMocks;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

@AutoConfigureMessageVerifier
@SpringBootTest
public class DirectMessagingControllerBase {

    @InjectMocks
    private DirectMessagingController controller;

    @MockBean
    private RabbitTemplate mockRabbitTemplate;

    public void queryAsyncEmployee() {
        controller.queryAsyncEmployee();
    }

}
