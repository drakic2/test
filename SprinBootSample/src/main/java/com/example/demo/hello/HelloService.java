package com.example.demo.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
	
	@Autowired
	private HelloRepository repository;
	
	/** Get one employee */
	public Employee getEmployee(String id) {
		
		//Search
		Map<String, Object> map = repository.findById(id);
		
		//Get value from Map
		String employeeId = (String) map.get("id");
		String name = (String) map.get("name");
		int age = (Integer) map.get("age");
		
		//Set the Employee class to a value
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeName(name);
		employee.setEmployeeAge(age);
		
		return employee;
		
		
	}

}
