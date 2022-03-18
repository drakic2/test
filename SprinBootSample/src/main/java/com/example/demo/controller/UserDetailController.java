package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j //for log
public class UserDetailController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper; //instantiated in JavaConfig.java class as a @Bean
	
	
	/**Display user details screen*/
	@GetMapping("/detail/{userId:.+}")
	public String getUser(UserDetailForm form, Model model, @PathVariable("userId") String userId) {
		
		//Get user
		MUser user = userService.getUserOne(userId);
		user.setPassword(null);
		
		//Get user
		form = modelMapper.map(user, UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		
		//Register in Model
		model.addAttribute("userDetailForm", form);
		
		//Display user details screen
		return "user/detail";
		
	}
	
	/** User update process */
	@PostMapping(value = "/detail", params = "update")
	public String updateUser(UserDetailForm form, Model model) {
		try {
		//Update user
		userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());
		} catch (Exception e) {
			log.error("Error in user update", e);
		}
		
		//redirect to user list screen
		return "redirect:/user/list";
	}
	
	/** User delete process */
	@PostMapping(value = "/detail", params = "delete")
	public String deleteUser(UserDetailForm form, Model model) {
		//delete user
		userService.deleteUserOne(form.getUserId());
		
		//redirect to user list screen
		return "redirect:/user/list";
	}
	
	
	


}