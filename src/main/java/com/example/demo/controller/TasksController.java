package com.example.demo.controller;

import com.example.demo.DTO.TaskDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Tasks;
import com.example.demo.repository.TaskinfoRepository;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class TasksController {

    @Autowired
    TaskService taskService;

    @PostMapping("/api/projects/{project_id}/tasks")
    public ResponseEntity<String> addNewtask(@RequestBody @Valid TaskDTO taskDTO, @PathVariable Integer project_id){
        taskService.createTask(taskDTO, project_id);
        return ResponseEntity.ok("Task " + taskDTO + " added successfully");
    }
    @GetMapping("/api/projects/{project_id}/tasks")
    public ResponseEntity<List<TaskDTO>> tasksListByProjectId(@PathVariable Integer project_id){
        return ResponseEntity.ok(taskService.returnTasksByProjectId(project_id));
    }

    @GetMapping("/api/projects/{project_id}/tasks/{task_id}")
    public ResponseEntity<List<TaskDTO>> taskByProjectIdAndTaskId(@PathVariable Integer project_id, @PathVariable Integer task_id) {
        taskService.returnTasksByProjectId(project_id);
        return ResponseEntity.ok(taskService.returnTaskByProjectAndTaskId(project_id, task_id));
    }

    @PutMapping("/api/projects/{project_id}/tasks/{task_id}")
    public ResponseEntity<String> changeDescriptionOfTask(@PathVariable Integer project_id, @PathVariable Integer task_id, @RequestParam(name = "description") String description){
        taskService.changeDescriptionOfTask(project_id, task_id, description);
        return ResponseEntity.ok("Description of task nr.: " + task_id + " updated successfully");
    }

    @DeleteMapping("/api/projects/{project_id}/tasks/{task_id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer project_id, @PathVariable Integer task_id){
        taskService.deleteTask(project_id, task_id);
        return ResponseEntity.ok("Task nr: " + task_id + "deleted successfully");
    }

    @PutMapping("/api/tasks/{task_id}/complete")
    public ResponseEntity<String> markTaskAsCompleted(@PathVariable Integer task_id, @RequestParam(name = "status") String status){
        status = "completed";
        taskService.markTaskCompleted(task_id, status);
        return ResponseEntity.ok("Status of the task's nr: " + task_id + " has been updated to 'completed'");
    }

    @PutMapping("/api/tasks/{task_id}/assign/{employee_id}")
    public ResponseEntity<String> assignTaskToEmployee(@PathVariable Integer task_id, @PathVariable Integer employee_id){
        taskService.assignTaskToEmployee(task_id, employee_id);
        return ResponseEntity.ok("Task nr:" + task_id + " assigned to employee nr: " + employee_id);
    }

    @GetMapping("/api/tasks/assigned/{employee_id}")
    public ResponseEntity<List<Tasks>> tasksListByEmployeeId(@PathVariable Integer employee_id){
        return ResponseEntity.ok(taskService.returnTasksByEmployeeId(employee_id));
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<List<Tasks>> filterTasksByStatus(@RequestParam (name = "status") String status) {
        List<Tasks> tasks = taskService.filterTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }
}
