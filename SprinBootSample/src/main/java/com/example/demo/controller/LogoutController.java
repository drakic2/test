package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LogoutController {
	
	/** Redirect to login screen */
	@PostMapping("/logout")
	public String postLogout() {
		
		log.info("Logout");
		return "redirect:/login"; //redirect means go to this site like we typed it inside browser
	}

}
