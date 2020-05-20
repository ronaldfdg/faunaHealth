package com.faunahealth.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.faunahealth.web.entity.User;
import com.faunahealth.web.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService serviceUser;
	
	@GetMapping("/")
	public String goHome(Authentication authentication, HttpSession session) {
		
		if(session.getAttribute("user") == null) {
			User user = serviceUser.findByUsername(authentication.getName());
			user.setPassword(null);
			session.setAttribute("user", user);
		}
		
		return "home";
	}
}
