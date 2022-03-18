package com.example.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.GroupOrder;
import com.example.demo.form.SignupForm;
import com.example.demo.form.UserDetailForm;
import com.example.demo.form.UserListForm;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	/** User search */
	@GetMapping("/get/list")
	public List<MUser> getUserList(UserListForm form) {
		
		//convert form to MUser class
		MUser user = modelMapper.map(form, MUser.class);
		
		//Get user list
		List<MUser> userList = userService.getUsers(user);
		
		return userList;
	}
	
	/** User signup */
	@PostMapping("/signup/rest")
	public RestResult postSignup(@Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult, Locale locale) {
		
		//input check result
		if(bindingResult.hasErrors()) {
			//Check result: NG
			Map<String, String> errors = new HashMap<String, String>();
			
			//Get error message
			for(FieldError error : bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			//Return error result
			return new RestResult(90, errors);
		}
		
		//Convert form to MUser class
		MUser user = modelMapper.map(form, MUser.class);
		
		//user signup
		userService.signup(user);
		
		//Return result
		return new RestResult(0, null);
	}
	
	/** Update user */
	@PutMapping("/update")
	public int updateUser(UserDetailForm form) {
		//Update user
		userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());
		
		return 0;
	}
	
	/** Delete user */
	@DeleteMapping("/delete")
	public int deleteUser(UserDetailForm form) {
		//Delete user
		userService.deleteUserOne(form.getUserId());
		
		return 0;
	}
	
}
