package com.example.demo.controller;

import com.example.demo.config.CustomConfigurationYAMLConfig;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeinfoRepository;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @Slf4j
    public class EmployeeController {
        @Autowired
        CustomConfigurationYAMLConfig customConfigurationYAMLConfig;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/api/register")
    public ResponseEntity<String> createEmployee(@RequestBody @Valid Employee employee){
        employeeService.createEmployee(employee);
        return ResponseEntity.ok("Employee" + employee + " successfully added");
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity<Employee> getEmployeeByID(@RequestParam(name = "employee_id") Integer user_id){
        return ResponseEntity.ok(employeeService.findEmployee(user_id));
    }

    @PutMapping("/api/user/profile")
    public ResponseEntity<String> updateEmployeeInfo(@RequestBody @Valid Employee employee, @RequestParam(name = "employee_id") Integer employee_id){
        employeeService.updateEmployeeInfo(employee_id, employee);
        return ResponseEntity.ok("Employee with id " + employee_id + " has been updated");
    }

    @PutMapping("/api/user/password")
    public ResponseEntity<String> updateEmployeePassword(@RequestParam(name = "password") Integer password, @RequestParam (name = "employee_id") Integer user_id){
        employeeService.updatePassword(user_id, password);
        return ResponseEntity.ok("Employee's password with id: " + user_id + " has been updated");
    }
//
//
    @DeleteMapping("/api/user/profile")
    public ResponseEntity<String> deleteEmployee(@RequestParam(name = "employee_id") Integer user_id){
        employeeService.deleteEmployee(user_id);
        return ResponseEntity.ok("Employee with id: " + user_id + " has been deleted");
    }

//

}
