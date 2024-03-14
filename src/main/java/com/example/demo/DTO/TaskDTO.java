package com.example.demo.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TaskDTO {

    @NotNull
    private Integer task_id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private LocalDate deadline;

    @NotEmpty
    private String status;

    private Integer fk_projects;





}
