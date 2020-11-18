package com.poc.rabbit.producerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//TODO: Add Test cases
//TODO: add test case for logs (look at other logging test cases)
//TODO: see if anything can be configurable and add to config file
//TODO: format code and add all log messages
//TODO: Rename class names where applicable
//TODO: rewrite readme
//TODO: convert spring boot apps to 1 docker-image so they can run directly from docker-compose
//TODO: Done :-)
@SpringBootApplication
public class ProducerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerServiceApplication.class, args);
    }

}
