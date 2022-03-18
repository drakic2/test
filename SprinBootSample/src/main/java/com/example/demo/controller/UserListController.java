package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.form.UserListForm;

@Controller
@RequestMapping("/user")
public class UserListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//Display user list screen
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model) {
		
		//Convert form to MUser class
		MUser user = modelMapper.map(form, MUser.class);
		
		//get user list
		List<MUser> userList = userService.getUsers(user);
		
		//Register in model
		model.addAttribute("userList", userList);

		
		//Display user list screen
		return "user/list";
	}
	
	
	/** User list search process*/
	@PostMapping("/list")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		
		//Convert form to MUser class
		MUser user = modelMapper.map(form, MUser.class);
		
		//Get user list
		List<MUser> userList = userService.getUsers(user);
		
		//Register in Model
		model.addAttribute("userList", userList);
		
		//Display useer list screen
		return "user/list";
	
	}

}
