package com.example.demo.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.UserApplicationService;
import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.GroupOrder;
import com.example.demo.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
	
	@Autowired
	private UserApplicationService userApplicationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper; //instantiated in JavaConfig.java class as a @Bean
	
	
	/**Display the user signup screen*/
	@GetMapping("/signup")
	public String getSignUp(Model model, //In model we can pass value to html page with ${key name}
			Locale locale, //Gets the language settings from the browser
			@ModelAttribute SignupForm form //Automatically makes model.addAtribute("signupForm", form);
			) {
		//Get gender
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		
		//Transition to user signup screen
		return "user/signup";
	}
	
	/** User signup process*/
	@PostMapping("/signup")
	public String postSignup(
			Model model, //In model here we can get values from html page from ${key name}
			Locale locale, //Gets the language settings from the browser
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form, 
			BindingResult bindingResult //must be after Model attribute
			) {
	
		
		//input check results
		if (bindingResult.hasErrors()) {
			//NG: return to the user signup screen
			return getSignUp(model, locale, form);
		}
		
		log.info(form.toString());
		
		//Convert form to Muser class
		MUser user = modelMapper.map(form, MUser.class);
		
		//user signup
		userService.signup(user);
		
		//redirect to login screen 
		return "redirect:/login";
	}
	
	
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
