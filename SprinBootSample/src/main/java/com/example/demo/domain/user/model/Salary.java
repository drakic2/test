package com.example.demo.domain.user.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity //JPA
@Table(name = "t_salary")   //JPA
public class Salary {
	
	//private String userId;   JPA
	//private String yearMonth;   JPA
	@EmbeddedId
	private SalaryKey salaryKey;
	private Integer salary;

}
