package com.spring.batch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Address {
	 /**
		 * 
		 */
		private static final long serialVersionUID = 7615618179099493105L;
		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE)
		private Integer id;
		
	    public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		private String employeeId;
	    private String firstName;
	    private String lastName;
	    private Integer age;
	    private String email;
	    
	    @ManyToOne
	    @JoinColumn(name="emp_id",referencedColumnName="id",nullable=false)
	    private Employee employee;
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
		public Employee getEmployee() {
			return employee;
		}
		public void setEmployee(Employee employee) {
			this.employee = employee;
		}
		@Override
		public String toString() {
			return "Address [id=" + id + ", employeeId=" + employeeId + ", firstName=" + firstName + ", lastName="
					+ lastName + ", age=" + age + ", email=" + email + ", employee=" + employee + "]";
		}
		
		
		

}
