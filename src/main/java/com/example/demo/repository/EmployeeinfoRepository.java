package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeinfoRepository extends CrudRepository<Employee, Integer> {

}
