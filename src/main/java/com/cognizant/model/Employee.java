package com.cognizant.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "firstName", "lastName", "gender", "dob", "joiningDate", "salary", "employeeNo" })
public class Employee {

	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("dob")
	private String dob;
	@JsonProperty("joiningDate")
	private String joiningDate;
	@JsonProperty("salary")
	private Integer salary;
	@JsonProperty("employeeNo")
	private Integer employeeNo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonProperty("dob")
	public String getDob() {
		return dob;
	}

	@JsonProperty("dob")
	public void setDob(String dob) {
		this.dob = dob;
	}

	@JsonProperty("joiningDate")
	public String getJoiningDate() {
		return joiningDate;
	}

	@JsonProperty("joiningDate")
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	@JsonProperty("salary")
	public Integer getSalary() {
		return salary;
	}

	@JsonProperty("salary")
	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@JsonProperty("employeeNo")
	public Integer getEmployeeNo() {
		return employeeNo;
	}

	@JsonProperty("employeeNo")
	public void setEmployeeNo(Integer employeeNo) {
		this.employeeNo = employeeNo;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", dob=" + dob
				+ ", joiningDate=" + joiningDate + ", salary=" + salary + ", employeeNo=" + employeeNo
				+ ", additionalProperties=" + "]";
	}

}