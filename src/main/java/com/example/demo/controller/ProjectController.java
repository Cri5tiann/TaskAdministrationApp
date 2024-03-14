package com.example.demo.controller;

import com.example.demo.model.Projects;
import com.example.demo.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ProjectController {


    @Autowired
    ProjectService projectService;

    @GetMapping("/api/projects")
    public ResponseEntity<List<Projects>> showAllProjects(){
        return ResponseEntity.ok(projectService.showProjects());
    }

    @PostMapping("/api/projects")
    public ResponseEntity<String> addNewProject(@RequestBody @Valid Projects project){
        projectService.createProject(project);
        return ResponseEntity.ok("Project " + project + " successfully added");
    }

    @GetMapping("/api/projects/{project_id}")
    public ResponseEntity<Projects> showProjectById(@PathVariable Integer project_id){
        return ResponseEntity.ok(projectService.showProjectById(project_id));
    }

    @PutMapping("/api/projects/{project_id}")
    public ResponseEntity<String> updateProjectById(@RequestBody @Valid Projects project,@PathVariable Integer project_id){
        projectService.updateProject(project_id, project);
        return ResponseEntity.ok("Project with id: " + project_id + " successfully updated");
    }

    @DeleteMapping("/api/projects/{project_id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable Integer project_id){
        projectService.deleteProject(project_id);
        return ResponseEntity.ok("Project with id: " + project_id + " successfully deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Projects>> searchProjectsByTitle(@RequestParam (name = "name") String keyword) {
        List<Projects> projects = projectService.searchProjectsByTitle(keyword);
        return ResponseEntity.ok(projects);
    }

//    @GetMapping("/byDateRangeStart")
//    public ResponseEntity<List<Projects>> getProjectsByDateRange1(
//            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
//            @RequestParam("finish_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish_date) {
//        List<Projects> projects = projectService.findProjectsByDateRange1(start_date, finish_date);
//        return ResponseEntity.ok(projects);
//    }
//
//    @GetMapping("/byDateRangeEnd")
//    public ResponseEntity<List<Projects>> getProjectsByDateRange2(
//            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
//            @RequestParam("finish_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish_date) {
//        List<Projects> projects = projectService.findProjectsByDateRange2(start_date, finish_date);
//        return ResponseEntity.ok(projects);
//    }

}
