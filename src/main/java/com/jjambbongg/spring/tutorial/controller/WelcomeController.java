package com.jjambbongg.spring.tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String welcome(String email, String name, Model model) {
		model.addAttribute("email", email);
		model.addAttribute("name", name);
		return "welcome";
	}
}
