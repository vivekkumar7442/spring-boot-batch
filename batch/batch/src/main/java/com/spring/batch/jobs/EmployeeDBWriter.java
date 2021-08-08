package com.spring.batch.jobs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.spring.batch.config.EmployeeReposi;
import com.spring.batch.dto.Employee;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmployeeReposi emprep;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		ParameterizedPreparedStatementSetter preparestate = new ParameterizedPreparedStatementSetter<Employee>() {
			public void setValues(PreparedStatement ps, Employee argument) throws SQLException {
				ps.setString(1, argument.getEmployeeId());
				ps.setString(2, argument.getFirstName());
				ps.setString(3, argument.getLastName());
				ps.setString(4, argument.getEmail());
				ps.setInt(5, argument.getAge());

			}
		};
		
		emprep.saveAll(employees);
		System.out.println("JPA REP SAVE-------------------------------------------------------------");


		for (Employee argument : employees) {
			String sql = "insert into employee "
					+ "(employee_id, first_name, last_name, email, age)  values(?,?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] { argument.getEmployeeId(), argument.getFirstName(),
					argument.getLastName(), argument.getEmail(), argument.getAge() });

			String childTableSql = "insert into address "
					+ "(employee_id, first_name, last_name, email, age)  values(?,?,?,?,?)";
			jdbcTemplate.update(childTableSql, new Object[] { argument.getEmployeeId(), argument.getFirstName(),
					argument.getLastName(), argument.getEmail(), argument.getAge() });
			

		}

	}

}
