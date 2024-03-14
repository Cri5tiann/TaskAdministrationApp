package com.example.demo.service;

import com.example.demo.DTO.TaskDTO;
import com.example.demo.DTOTransformer.TaskDTOTransformer;
import com.example.demo.model.Employee;
import com.example.demo.model.Projects;
import com.example.demo.model.Tasks;
import com.example.demo.repository.ProjectinfoRepository;
import com.example.demo.repository.TaskinfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    @Autowired
    TaskinfoRepository taskinfoRepository;
    @Autowired
    ProjectinfoRepository projectinfoRepository;

    @Autowired
    TaskDTOTransformer taskDTOTransformer;





    public void createTask(TaskDTO taskDTO, Integer project_id){
        Projects projects = projectinfoRepository.findById(project_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        taskDTO.setFk_projects(project_id);
        Tasks tasks = TaskDTOTransformer.convertToDBModel(taskDTO, projects);
        taskinfoRepository.save(tasks);
    }

    public List<TaskDTO> returnTasksByProjectId(Integer project_id){
        List<Tasks> TaskList = (List<Tasks>) taskinfoRepository.findAll();


        return TaskList.stream()
                .map(TaskDTOTransformer::convertToDTO)
                .filter(e -> e.getFk_projects().equals(project_id))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> returnTaskByProjectAndTaskId(Integer project_id, Integer task_id) {
        List<Tasks> TaskList = (List<Tasks>) taskinfoRepository.findAll();

        return TaskList.stream()
                .map(TaskDTOTransformer::convertToDTO)
                .filter(e -> e.getFk_projects().equals(project_id))
                .filter(e -> e.getTask_id().equals(task_id))
                .collect(Collectors.toList());
    }

    public void changeDescriptionOfTask(Integer project_id, Integer task_id, String description){
        Tasks foundTask = taskinfoRepository.findById(task_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        foundTask.setId(task_id);
        foundTask.setDescription(description);
        taskinfoRepository.save(foundTask);
    }

    public void deleteTask(Integer project_id, Integer task_id){
        Tasks foundTask = taskinfoRepository.findById(task_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        foundTask.setProjects(null);
        taskinfoRepository.deleteById(task_id);
    }

    public void markTaskCompleted(Integer task_id, String status){
        Tasks foundTask = taskinfoRepository.findById(task_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        foundTask.setId(task_id);
        foundTask.setStatus(status);
        taskinfoRepository.save(foundTask);
    }

    public void assignTaskToEmployee(Integer task_id, Integer employee_id){
        Tasks foundTask = taskinfoRepository.findById(task_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        foundTask.setId(task_id);
        foundTask.setEmployees(employee_id);
        taskinfoRepository.save(foundTask);
    }

    public List<Tasks> returnTasksByEmployeeId(Integer employee_id){
        List<Tasks> TaskList = (List<Tasks>) taskinfoRepository.findAll();

        return TaskList.stream()
                .filter(e -> e.getEmployees().equals(employee_id))
                .collect(Collectors.toList());
    }

    public List<Tasks> filterTasksByStatus(String status) {
        return taskinfoRepository.findByStatus(status);
    }

}


