package com.poc.rabbit.producerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//TODO: add one more service and have a message from producer directly to it and another going from producer to direct consumer to the new one
//TODO: add swagger-ui as default link when producer service root url is called
//TODO: see if anything can be configurable and add to config file
//TODO: Add Test cases
//TODO: add test case for logs (look at other logging test cases)
//TODO: format code and add all log messages
//TODO: rewrite readme
//TODO: convert spring boot apps to 1 docker-image so they can run directly from docker-compose
@SpringBootApplication
public class ProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerServiceApplication.class, args);
    }

}
