package com.jjambbongg.spring.tutorial.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjambbongg.spring.tutorial.domain.Question;
import com.jjambbongg.spring.tutorial.domain.QuestionRepository;
import com.jjambbongg.spring.tutorial.domain.User;
import com.jjambbongg.spring.tutorial.web.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		Question question = new Question(HttpSessionUtils.getUserFromSession(session), title, contents);
		questionRepository.save(question);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String list(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String modify(@PathVariable Long id, String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		
		if(question.isSameWriter(loginUser)) {
			
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		questionRepository.delete(id);
		return "redirect:/";
	}
}
