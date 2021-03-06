package com.jjambbongg.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jjambbongg.spring.tutorial.domain.QuestionRepository;

@Controller
public class HomeController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("question", questionRepository.findAll());		
		return "index";
	}
}
