package com.poc.rabbit.producerservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    String employeeId;
    String firstName;
    String lastName;
    String phoneNumber;
}
