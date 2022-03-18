package com.example.demo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	@Autowired
	private HelloService service;
	
	@GetMapping("/hello")
	public String getHello() {
		//Screen transition to hello.html
		return "hello";
	}
	
	@PostMapping("/hello")
	public String postRequest(@RequestParam("text1") String str, Model model) {
		//Register the string received from screen to Model
		model.addAttribute("sample", str);
		
		//Screen transition to response.html
		return "hello/response";
	}
	
	
	@PostMapping("/hello/db")
	public String postDbRequest(@RequestParam("text2") String id, Model model) {
		
		//Search one
		Employee employee = service.getEmployee(id);
		
		//Save search result to Model
		model.addAttribute("employee", employee);
		
		//Screen transition to db.html
		return "hello/db";
		
	}
	

}