package com.example.exampleappmicroservice.Services.ServicesImpl;


import com.example.exampleappmicroservice.Models.Disponibilites;
import com.example.exampleappmicroservice.Models.Employee;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeCustomerService {
    @Autowired
    private  EmployeeServiceClient employeeServiceClient;

    public EmployeeCustomerService(EmployeeServiceClient employeeServiceClient) {
        this.employeeServiceClient = employeeServiceClient;
    }

    @CircuitBreaker(name = "notification-app", fallbackMethod = "fallbackMethod")
    public List<Employee> getAllEmployees() {
        return employeeServiceClient.getAllEmployees();
    }


    public List<Employee> fallbackMethod(Exception e) {
        Employee fallbackEmployee = new Employee();
        fallbackEmployee.setFirstName("Fallback Employee");
        fallbackEmployee.setLastName("notif microservice app is down!");

        return Collections.singletonList(fallbackEmployee);
    }

    public List<Map<Disponibilites, Employee>> getPlanningByDay(Disponibilites jour) {
        return employeeServiceClient.getPlanningByDayFromMs(jour);
    }
}
