package com.example.exampleappmicroservice.DAO.elasticsearch;


import com.example.exampleappmicroservice.Documents.EmployeeDocument;
import com.example.exampleappmicroservice.Models.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeESDao extends ElasticsearchRepository<EmployeeDocument, String> {


    List<EmployeeDocument> findByFirstName(String firstName);
}

