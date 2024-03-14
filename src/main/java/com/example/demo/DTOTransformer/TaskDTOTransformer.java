package com.example.demo.DTOTransformer;

import com.example.demo.DTO.TaskDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Projects;
import com.example.demo.model.Tasks;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOTransformer {

    public static TaskDTO convertToDTO(Tasks tasks){

        return TaskDTO.builder()
                .task_id(tasks.getId())
                .name(tasks.getName())
                .description(tasks.getDescription())
                .deadline(tasks.getDeadline())
                .status(tasks.getStatus())
                .fk_projects(tasks.getProjects().getId())
                .build();
    }

    public static Tasks convertToDBModel(TaskDTO taskDTO, Projects projects){

        return Tasks.builder()
                .id(taskDTO.getTask_id())
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .deadline(taskDTO.getDeadline())
                .status(taskDTO.getStatus())
                .projects(projects)
                .build();
    }
}
