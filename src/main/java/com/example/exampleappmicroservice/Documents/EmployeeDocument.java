package com.example.exampleappmicroservice.Documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "employees")
public class EmployeeDocument {


    public EmployeeDocument(){}
    @Id
    private String id;
    private String firstName;


//    @Field(type = FieldType.Completion, name = "firstNameSuggest")
//    private Completion firstNameSuggest;
//
//    // other fields, getters, setters...
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//        this.firstNameSuggest = new Completion(new String[]{firstName});
//    }
    private String lastName;
    private double age;
    private Date anneeTravail;
    private double salary;
    public EmployeeDocument(String id, String firstName, String lastName, double age, Date anneeTravail, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.anneeTravail = anneeTravail;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public Date getAnneeTravail() {
        return anneeTravail;
    }

    public void setAnneeTravail(Date anneeTravail) {
        this.anneeTravail = anneeTravail;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
