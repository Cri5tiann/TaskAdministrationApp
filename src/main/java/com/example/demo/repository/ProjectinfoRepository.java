package com.example.demo.repository;

import com.example.demo.model.Projects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectinfoRepository extends CrudRepository<Projects, Integer>{
    List<Projects> findByNameContaining(String keyword);

//    List<Projects> findByStartDateBetween(LocalDate start_date, LocalDate finish_date);
//    List<Projects> findByEndDateBetween(LocalDate start_date, LocalDate finish_date);
}
