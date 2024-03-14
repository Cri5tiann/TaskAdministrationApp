package com.example.demo;


import com.example.demo.controller.EmployeeController;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    Employee employee;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
        employee = Employee.builder()
                .id(1)
                .name("TestName")
                .surname("TestSurname")
                .password(2256)
                .build();
    }



    @Test
    public void testCreateEmployeeEndpoint() throws Exception {

        String employeeJson = objectMapper.writeValueAsString(employee);


        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(employeeJson))
                .andExpect(status().is(200)
                );

        Mockito.verify(employeeService, Mockito.times(1)).createEmployee(employee);
    }

    @Test
    public void testGetEmployeeById() throws Exception{

        final Integer id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/profile")
                        .param("employee_id", String.valueOf(1))
        ).andExpect(status().is(200));

        Mockito.verify(employeeService, Mockito.times(1)).findEmployee(id);
    }

    @Test
    public void testUpdateEmployeeById() throws Exception{

        final Integer id = 1;


        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user/profile")
                        .param("employee_id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(employeeService, Mockito.times(1)).updateEmployeeInfo(id, employee);
    }

    @Test
    public void testUpdateEmployeePasswordById() throws Exception{

        final Integer id = 1;
        final Integer password1 = 2234;


        String employeeJson = objectMapper.writeValueAsString(employee);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user/password")
                        .param("password", String.valueOf(2234))
                        .param("employee_id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
        ).andExpect(status().is(200));

        Mockito.verify(employeeService, Mockito.times(1)).updatePassword(id, password1);
    }

    @Test
    public void testDeleteEmployeeById() throws Exception{

        final Integer id = 1;

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/user/profile")
                        .param("employee_id", String.valueOf(1))
        ).andExpect(status().is(200));

        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployee(id);
    }

}
