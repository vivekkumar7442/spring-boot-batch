package com.spring.batch.processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.spring.batch.dto.EmployeeDTO;
import com.spring.batch.entity.Address;
import com.spring.batch.entity.Employee;
import com.spring.batch.runner.JobRunner;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeProcessor.class);


    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        logger.info("inside processor employee details:{} " , employee.toString());
        
        Address address = new Address();
        address.setEmployeeId(employeeDTO.getEmployeeId());
        address.setFirstName(employeeDTO.getFirstName());
        address.setLastName(employeeDTO.getLastName());
        address.setEmail(employeeDTO.getEmail());
        address.setAge(employeeDTO.getAge());
        address.setEmployee(employee);
        employee.getAddress().add(address);
        logger.info("inside processor address details:{} " , address.toString());
        return employee;
    }
}