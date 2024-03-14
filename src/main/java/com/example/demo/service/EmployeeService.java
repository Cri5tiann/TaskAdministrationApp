package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeinfoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeinfoRepository employeeInfoRepository;


    public void createEmployee(Employee employee){
        employeeInfoRepository.save(employee);
        log.info("Employee" + employee + " has been created");
    }

    public Employee findEmployee(Integer id){
         return employeeInfoRepository.findById(id)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }


    public void updateEmployeeInfo(Integer id, Employee employee){
        Employee employee1 = employeeInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        employee.setId(id);
        employeeInfoRepository.save(employee);
        log.info("Employee" + employee + " has been updated");
    }

    public void updatePassword(Integer id, Integer password){
        Employee employee = employeeInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        employee.setId(id);
        employee.setPassword(password);
        employeeInfoRepository.save(employee);
    }

    public void deleteEmployee(Integer id){
        employeeInfoRepository.deleteById(id);
        log.info("Employee " + id + " has been deleted");
    }
}
