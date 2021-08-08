package com.spring.batch.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.batch.dto.Employee;

@Repository
public interface EmployeeReposi extends JpaRepository<Employee, Integer>{

}
