package com.spring.batch.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.spring.batch.entity.Employee;
import com.spring.batch.repo.EmployeeReposi;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDBWriter.class);


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmployeeReposi emprep;
	
    @Autowired
	private ExecutionContext executionContext;

	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		/*customer writer with preparestatement but not used for parent child JPA single update*/

		ParameterizedPreparedStatementSetter preparestate = new ParameterizedPreparedStatementSetter<Employee>() {
			public void setValues(PreparedStatement ps, Employee argument) throws SQLException {
				ps.setString(1, argument.getEmployeeId());
				ps.setString(2, argument.getFirstName());
				ps.setString(3, argument.getLastName());
				ps.setString(4, argument.getEmail());
				ps.setInt(5, argument.getAge());

			}
		};
		logger.info("Batchsize initial-------------------------------------------------------------:{}",executionContext.get("batchsize"));
		executionContext.put("batchsize",employees.size());
		logger.info("Batchsize size after adding employee-------------------------------------------------------------:{}",executionContext.get("batchsize"));

		/*customer writer for parent child JPA single update*/

		for (Employee argument : employees) {
			
			Employee savedEmployee=emprep.save(argument);
			Employee dbUnsave=	emprep.findById(savedEmployee.getId()).get();
			logger.info("JPA REP SAVE----------------------------:{}" , dbUnsave.getEmployeeId());

		}
		/*customer write for parent child JPA update*/
		emprep.saveAll(employees);
		logger.info("JPA REP SAVE ALL-------------------------------------------------------------");

/*customer write for parent child update*/
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
