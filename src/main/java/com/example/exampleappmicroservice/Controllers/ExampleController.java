package com.example.exampleappmicroservice.Controllers;

import com.example.exampleappmicroservice.DAO.EmployeeDao;
import com.example.exampleappmicroservice.DTO.EmployeeDto;
import com.example.exampleappmicroservice.Documents.EmployeeDocument;
import com.example.exampleappmicroservice.Models.Disponibilites;
import com.example.exampleappmicroservice.Models.Employee;
import com.example.exampleappmicroservice.Services.ServicesImpl.EmployeeCustomerService;
import com.example.exampleappmicroservice.Services.ServicesImpl.EmployeeServiceClient;
import com.example.exampleappmicroservice.Services.ServicesImpl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ExampleController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping(value = "/getexternaldata", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> viewEmail(
    ) {
        ResponseEntity<String> result = restTemplate.exchange("http://localhost:8088/", HttpMethod.GET, null, String.class);
        return ResponseEntity.ok(result.getBody());
    }

//    @GetMapping(value = "/allEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<String> getAllEmployees(
//    ) {
//        ResponseEntity<String> result = restTemplate.exchange("http://localhost:8088/all", HttpMethod.GET, null, String.class);
//        return ResponseEntity.ok(result.getBody());
//    }

    @Autowired
    EmployeeDao employeeDao;
    @GetMapping(value = "/all")
    public List<Employee> getAllEmployees(
    ) {
        return employeeDao.findAll();
    }

    @Autowired
    EmployeeServiceImpl employeeService;
//    @GetMapping(value = "/findbyfirstname/{firstName}")
//    public List<EmployeeDocument> getAllEmployees(@PathVariable("firstName")   String firstName
//    ) {
//        return employeeService.findByFirstName(firstName);
//    }
    @PostMapping(value="/addEmployee",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto emp){
        //ResponseEntity<String> result = restTemplate.exchange("http://localhost:8088/add", HttpMethod.POST, emp, String.class);
        ResponseEntity<Employee> response = restTemplate.postForEntity(
                "http://localhost:8088/add",
                emp,
                Employee.class);
        return ResponseEntity.ok(response.getBody());


    }
    @GetMapping("/searchByAgeRange")
    public List<EmployeeDocument> searchByAgeRange(@RequestParam int min, @RequestParam int max) {
        return employeeService.searchByAgeRange(min, max);
    }
    @GetMapping("/searchByFirstNameAndAge")
    public List<EmployeeDocument> searchByFirstNameAndAge(@RequestParam String firstName, @RequestParam int max) {
        return employeeService.searchByLastNameAndAge(firstName, max);
    }
    @GetMapping("/searchByFirstNameBeginWith")
    public List<EmployeeDocument> searchByFirstNameBeginWith(@RequestParam String sequence) {
        return employeeService.searchByFirstNameBeginWith(sequence);
    }

    @GetMapping("/searchByLastName")
    public List<EmployeeDocument> searchByName(@RequestParam String name) {
        return employeeService.searchByLastName(name);
    }

    @GetMapping("/searchByFirstNameLike")
    public List<EmployeeDocument> searchByFirstNameLike(@RequestParam String name) {
        return employeeService.searchByFirstNameLike(name);
    }

    @GetMapping("/searchByMulti")
    public List<EmployeeDocument> searchByMulti(@RequestParam String name) {
        return employeeService.searchByMulti(name);
    }
    @GetMapping("/admin")
   // @PreAuthorize("hasRole('admin')")
    public String adminPage() {
        return "Admin Page";
    }
    @Autowired
    private EmployeeCustomerService employeeServiceClient;



    @GetMapping("/test")
    public List<Employee> testClient() {
        return employeeServiceClient.getAllEmployees();
    }


    @GetMapping("/planningbyday/{jour}")
    public List<Map<Disponibilites, Employee>> getPlanningByDay(@PathVariable("jour")  Disponibilites jour) {
        return employeeServiceClient.getPlanningByDay( jour);
    }
}
