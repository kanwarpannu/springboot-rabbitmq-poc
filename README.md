# springboot-rabbitmq-poc (WIP)
Spring boot/Java POC using rabbitmq dependencies coming from spring-boot-starter-amqp.  

  
---------------------------------------------  
```

Shop(sell weapons and shields max of 10 each(rest api))  

Shop(separate direct exchanges for weapons, shields) --(rpc call, timeout less than rest call's)-> (separate direct queues for weapons, shields) storage  
  
if inventory below 10, starts with 20  
storage(topic exchange) --(async call)--> (queues for weapons, shields) distributor  

distributor(DB with 100 of everything)
```  
  
----------------------------------------------
  
//todo's:
3. Write test which uses a mock RabbitMQ(for fanout exchange testing):  https://github.com/fridujo/rabbitmq-mock  
4. See if we can mock rabbitmq using spring-rabbit-test (for direct exchange testing).
5. Use spies for capture of messages? for topic exchange testing 
6. Force usage of different types of exchanges?  
7. Whose responsibility is to bind queue to an exchange. producer or consumer or both? check this link  
https://gigi.nullneuron.net/gigilabs/rabbitmq-who-creates-the-queues-and-exchanges/  
8. Create exchange, queues and bindings through cli using below link:  
https://stackoverflow.com/questions/4545660/rabbitmq-creating-queues-and-bindings-from-a-command-line  
9. If queues don't exist when message is posted to exchange then the message is lost.
10. dlq's can be used in 9th case.  
11. Spring sleuth to trace message. 
12. Spring cloud contracts
13. do logging
14. Groovy acceptance tests
15. Rabbit Sync is also called rabbit rpc call

download zipkin from docker `docker run -d -p 9411:9411 openzipkin/zipkin:2.21`  
Note: Zipkin is used for tracing and checking performance, NOT as central logging 
elastic search-kibana added to docker-compose (takes 1 min to start)  
elastic search is where all logs get stored and indexed, kibana is used to visualize  
logstash is used as pipeline redirect all logs to elastic search  
https://cassiomolin.com/2019/06/30/log-aggregation-with-spring-boot-elastic-stack-and-docker/     

In this project we using zipkin for tracing and elk stack for logging  
    
In application, we imported logstash dependency and added logback.xml(to create a bean for it) and asked it to emit it to logstash at 5044  
  
In the docker, we created logstash and configured it using logstash.conf

