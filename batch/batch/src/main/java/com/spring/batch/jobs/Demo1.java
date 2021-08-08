package com.spring.batch.jobs;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.spring.batch.dto.Employee;
import com.spring.batch.dto.EmployeeDTO;
import com.spring.batch.dto.EmployeeFileRowMapper;
import com.spring.batch.processor.EmployeeProcessor;

@Configuration
public class Demo1 {

	private JobBuilderFactory jobBuilderFactory;
	private StepBuilderFactory stepBuilderFactory;
	private EmployeeProcessor employeeProcessor;
	private DataSource dataSource;
	
	@Autowired
	private EmployeeDBWriter employeeDBWriter;

	@Autowired
	public Demo1(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			EmployeeProcessor employeeProcessor, DataSource dataSource) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.employeeProcessor = employeeProcessor;
		this.dataSource = dataSource;
	}

	@Qualifier(value = "demo1")
	@Bean
	public Job demo1Job() throws Exception {
		return this.jobBuilderFactory.get("demo1").start(step1Demo1()).build();
	}

	@Bean
	public Step step1Demo1() throws Exception {
		return this.stepBuilderFactory.get("step1").<EmployeeDTO, Employee>chunk(5).reader(employeeReader())
				.processor(employeeProcessor).writer(employeeDBWriter).build();
	}


	@Bean
	@StepScope
	public FlatFileItemReader<EmployeeDTO> employeeReader() throws Exception {
		FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<>();
		reader.setResource(new PathResource("D:\\spring-batch\\batch\\batch\\src\\main\\resources\\employee.csv"));
		reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "firstName", "lastName", "email", "age");
						setDelimiter(",");
					}
				});
				setFieldSetMapper(new EmployeeFileRowMapper());
			}
		});
		return reader;
	}

	@Bean
	public JdbcBatchItemWriter<Employee> childWriter() {
		JdbcBatchItemWriter<Employee> myWriter = new JdbcBatchItemWriter<Employee>();
		myWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		myWriter.setSql(
				"insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
		myWriter.setDataSource(dataSource);
		myWriter.afterPropertiesSet();
		return myWriter;
	}

	@Bean
	public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
		JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return itemWriter;
	}

	@Bean
	public CompositeItemWriter<Employee> compositeItemWriter() {
		CompositeItemWriter<Employee> writer = new CompositeItemWriter<Employee>();
		writer.setDelegates(Arrays.asList(employeeDBWriterDefault(), childWriter()));
		return writer;
	}
}