package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "surname")
    private String surname;


    @Column(name = "password")
    private Integer password;

//    @OneToOne(mappedBy = "employees")
//    private Tasks tasks;

}


