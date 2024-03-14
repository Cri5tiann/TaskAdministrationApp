package com.example.demo;

import com.example.demo.DTO.TaskDTO;
import com.example.demo.controller.TasksController;
import com.example.demo.model.Tasks;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class TaskControllerTest {

    @InjectMocks
    private TasksController tasksController;

    @Mock
    private TaskService taskService;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    Tasks task;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(tasksController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        task = Tasks.builder()
                .name("Test update name")
                .description("Test update description")
                .deadline(LocalDate.of(2010, 7, 12))
                .status("Test update status")
                .build();
    }

    @Test
    public void testCreateTaskEndpoint() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setTask_id(1);
        task.setName("Sample Task");
        task.setDescription("Task Description");
        task.setDeadline(LocalDate.of(2014, 7, 12));
        task.setStatus("inProgress");

        String taskJson = objectMapper.writeValueAsString(task);
        final Integer project_id = 1;

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/projects/{project_id}/tasks",project_id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().is(200)
                );

        Mockito.verify(taskService, Mockito.times(1)).createTask(task, project_id);
    }

    @Test
    public void testGetTasksListByProjectIdEndPoint() throws Exception{

        final Integer project_id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/projects/{project_id}/tasks", project_id)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).returnTasksByProjectId(project_id);
    }

    @Test
    public void testGetTaskByProjectIdAndTaskId() throws Exception{


        final Integer project_id = 1;
        final Integer task_id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/projects/{project_id}/tasks/{taskId}", project_id, task_id)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).returnTasksByProjectId(task_id);
    }

    @Test
    public void testUpdateTaskInfoByProjectAndTaskId() throws Exception{

        TaskDTO task = new TaskDTO();
        task.setName("Test name");
        task.setDescription("test description");
        task.setDeadline(LocalDate.of(2018, 6, 4));
        task.setStatus("InProgress");

        final Integer project_id = 1;
        final Integer task_id = 1;
        final String description ="a simple test description";

        String employeeJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/projects/{projectId}/tasks/{taskId}", project_id, task_id)
                        .param("description", description)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).changeDescriptionOfTask(project_id, task_id, description);
    }

    @Test
    public void testDeleteTaskByProjectIdAndTaskId() throws Exception{

        final Integer project_id = 1;
        final Integer task_id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/projects/{project_id}/tasks/{task_id}", project_id, task_id)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).deleteTask(project_id, task_id);
    }

    @Test
    public void testMarkTaskAsCompleted() throws Exception{

        TaskDTO task = new TaskDTO();
        task.setName("Test name");
        task.setDescription("test description");
        task.setDeadline(LocalDate.of(2018, 6, 4));
        task.setStatus("InProgress");

        final Integer task_id = 1;
        final String status ="completed";

        String employeeJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/tasks/{task_id}/complete", task_id)
                        .param("status", status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).markTaskCompleted(task_id, status);
    }

    @Test
    public void testAssignTaskToEmployee() throws Exception{

        final Integer employee_id = 1;
        final Integer task_id = 1;

        String employeeJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/tasks/{task_id}/assign/{employee_id}", task_id, employee_id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).assignTaskToEmployee(task_id, employee_id);
    }

    @Test
    public void testTasksListByEmployeeId() throws Exception{

        final Integer employee_id = 1;

        String employeeJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/tasks/assigned/{employee_id}", employee_id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).returnTasksByEmployeeId(employee_id);

    }

    @Test
    public void testFilterByStatus() throws Exception{

        final String status = "inProgessss";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/filterByStatus")
                        .param("status", status)
        ).andExpect(status().is(200));

        Mockito.verify(taskService, Mockito.times(1)).filterTasksByStatus(status);
    }
}
