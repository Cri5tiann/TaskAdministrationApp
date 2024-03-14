package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.controller.ProjectController;
import com.example.demo.model.Employee;
import com.example.demo.model.Projects;
import com.example.demo.model.Tasks;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    Projects projects;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        projects = Projects.builder()
                .id(1)
                .name("Test update name")
                .description("Test update description")
                .start_date(LocalDate.of(2003, 10, 12))
                .finish_date(LocalDate.of(2007, 11, 10))
                .build();
    }

    @Test
    public void testCreateProjectEndpoint() throws Exception {

        String employeeJson = objectMapper.writeValueAsString(projects);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/projects")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(employeeJson))
                .andExpect(status().is(200)
                );

        Mockito.verify(projectService, Mockito.times(1)).createProject(projects);
    }

    @Test
    public void testGetAllProjectsEndpoint() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/projects")
        ).andExpect(status().is(200));

        Mockito.verify(projectService, Mockito.times(1)).showProjects();
    }

    @Test
    public void testGetProjectById() throws Exception{

        final Integer id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/projects/" + id)
        ).andExpect(status().is(200));

        Mockito.verify(projectService, Mockito.times(1)).showProjectById(id);
    }

    @Test
    public void testUpdateProjectById() throws Exception{

        final Integer id = 1;


        String projectJson = objectMapper.writeValueAsString(projects);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/projects/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        ).andExpect(status().is(200));

        Mockito.verify(projectService, Mockito.times(1)).updateProject(id, projects);
    }

    @Test
    public void testDeleteProjectById() throws Exception{

        final Integer id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/projects/" + id)
        ).andExpect(status().is(200));

        Mockito.verify(projectService, Mockito.times(1)).deleteProject(id);
    }

    @Test
    public void testSearchProjectsByName() throws Exception{

        String name ="GGOG";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/search")
                        .param("name", name)
        ).andExpect(status().is(200));

        Mockito.verify(projectService, Mockito.times(1)).searchProjectsByTitle(name);
    }

}
