package com.example.exampleappmicroservice.DAO;

import com.example.exampleappmicroservice.Models.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee,Integer> {

    List<Employee> findByFirstName(String firstName);
}
