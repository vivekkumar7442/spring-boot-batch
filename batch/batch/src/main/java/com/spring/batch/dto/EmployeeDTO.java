package com.spring.batch.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2343272421306852755L;
	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private Integer age;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}