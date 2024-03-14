package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "tasks")
public class Tasks {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "deadline")
    private LocalDate deadline;

    @NotEmpty
    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_projects", referencedColumnName = "project_id")
    private Projects projects;


    @Column(name = "fk_employees")
    private Integer employees;


}
