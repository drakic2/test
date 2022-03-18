package com.example.demo.domain.user.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class SalaryKey implements Serializable {  //JPA
	
	private String userId; //both are keys
	private String yearMonth;

}
