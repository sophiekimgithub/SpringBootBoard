package com.jjambbongg.spring.tutorial.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjambbongg.spring.tutorial.domain.User;
import com.jjambbongg.spring.tutorial.domain.UserRepository;
import com.jjambbongg.spring.tutorial.web.HttpSessionUtils;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm") 
	public String loginForn() {
		return "/user/login";
	}
	
	@PostMapping("/login") 
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user==null) {
			return "redirect:/users/loginForm";
		}
		if(!user.matchPassword(password)) {
			return "redirect:/users/loginForm"; 
		}
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}
	
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		if(!loginedUser.matchId(id)) {
			throw new IllegalStateException("You can modify own information only.");
		}
		
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String modify(@PathVariable Long id, User updatedUser, HttpSession session) {
		
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User loginedUser = HttpSessionUtils.getUserFromSession(session);
		if(!loginedUser.matchId(id)) {
			throw new IllegalStateException("You can modify own information only.");
		}
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}
