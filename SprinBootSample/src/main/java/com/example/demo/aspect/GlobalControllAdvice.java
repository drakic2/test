package com.example.demo.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllAdvice {
	
	/** Database-related exception handling*/
	@ExceptionHandler(DataAccessException.class) 
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		//set an empty string
		model.addAttribute("error", "");
		
		//register message in Model
		model.addAttribute("message", "An exception occured in SignupController");
		
		//register HTTP error code(500) in Model
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}
	
	/** Other exception handling*/
	@ExceptionHandler(Exception.class) 
	public String exceptionHandler(Exception e, Model model) {
		
		//set an empty string
		model.addAttribute("error", "");
		
		//register message in Model
		model.addAttribute("message", "An exception occured in SignupController");
		
		//register HTTP error code(500) in Model
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return "error";
		
	}

}
