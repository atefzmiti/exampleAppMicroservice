package com.example.exampleappmicroservice.Services.ServicesImpl;

import com.example.exampleappmicroservice.Models.Disponibilites;
import com.example.exampleappmicroservice.Models.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notification-app", url = "http://localhost:8088")
public interface EmployeeServiceClient {

    @GetMapping("/all")
    List<Employee> getAllEmployees();

    @GetMapping(value = "/emp/{jour}")
    List<Map<Disponibilites, Employee>> getPlanningByDayFromMs(@PathVariable("jour") Disponibilites jour
    );
}

