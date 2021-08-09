package com.spring.batch.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.batch.entity.Employee;

@Repository
public interface EmployeeReposi extends JpaRepository<Employee, Integer>{
	
	Employee findByEmployeeId(String empId);

}
