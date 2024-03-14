package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.Projects;
import com.example.demo.model.Tasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskinfoRepository extends CrudRepository<Tasks, Integer> {
    List<Tasks> findByStatus(String status);
}
