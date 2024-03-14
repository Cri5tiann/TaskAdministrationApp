package com.example.demo.service;

import com.example.demo.model.Projects;
import com.example.demo.repository.ProjectinfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ProjectService {

    @Autowired
    ProjectinfoRepository projectinfoRepository;

    public List<Projects> showProjects(){
        log.info("Returning all projects");
        return (List<Projects>) projectinfoRepository.findAll();
    }

    public void createProject(Projects project){
        projectinfoRepository.save(project);
        log.info("Project" + project + " has been created");
    }
    public Projects showProjectById(Integer id){
        return  projectinfoRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void updateProject(Integer id, Projects project){
        Projects project1 = projectinfoRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST));

        project.setId(id);
        projectinfoRepository.save(project);
        log.info("Project with id" + id + " has been updated");
    }
    public void deleteProject(Integer id){
        projectinfoRepository.deleteById(id);
        log.info("Project with id: " + id + " has been deleted");
    }

    public List<Projects> searchProjectsByTitle(String keyword) {
        return projectinfoRepository.findByNameContaining(keyword);
    }

//    public List<Projects> findProjectsByDateRange1(LocalDate start_date, LocalDate finish_date) {
//        return projectinfoRepository.findByStartDateBetween(start_date, finish_date);
//    }
//
//    public List<Projects> findProjectsByDateRange2(LocalDate start_date, LocalDate finish_date) {
//        return projectinfoRepository.findByEndDateBetween(start_date, finish_date);
//    }


}
