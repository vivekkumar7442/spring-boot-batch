package com.spring.batch.processor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.spring.batch.dto.Address;
import com.spring.batch.dto.Employee;
import com.spring.batch.dto.EmployeeDTO;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId()+ Math.random());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        System.out.println("inside processor " + employee.toString());
        
        Address address = new Address();
        address.setEmployeeId(employeeDTO.getEmployeeId()+ Math.random());
        address.setFirstName(employeeDTO.getFirstName());
        address.setLastName(employeeDTO.getLastName());
        address.setEmail(employeeDTO.getEmail());
        address.setAge(employeeDTO.getAge());
        employee.getAddress().add(address);
        System.out.println("inside processor " + employee.toString());
        return employee;
    }
}