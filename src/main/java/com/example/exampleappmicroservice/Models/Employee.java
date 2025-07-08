package com.example.exampleappmicroservice.Models;



import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.*;
import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class Employee {


    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @Column(name = "ID")
    private int id;


    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "AGE")
    private double age;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "ANNEE_TRAVAIL")
    private Date anneeTravail;



    @Column(name = "SALARY")
    private double salary;

}
