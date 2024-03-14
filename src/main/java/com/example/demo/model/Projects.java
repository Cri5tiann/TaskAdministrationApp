package com.example.demo.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "projects")
public class Projects {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate start_date;

    @Column(name = "finish_date")
    private LocalDate finish_date;

//    @OneToOne(mappedBy = "projects")
//    private Tasks tasks;
}
