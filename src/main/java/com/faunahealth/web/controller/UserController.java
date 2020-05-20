package com.faunahealth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.User;
import com.faunahealth.web.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("users", serviceUser.findAll());
		return "users/listUsers";
	}
	
	@GetMapping("/register")
	public String register(@ModelAttribute User user) {
		return "users/formUser";
	}
	
	@GetMapping("/lockOrUnlock/{id}")
	public String lockOrUnlock(@PathVariable("id") int id, RedirectAttributes attribute) {
		
		User user = serviceUser.findById(id);
		
		if(user.isStatus()) {
			user.setStatus(false);
			attribute.addFlashAttribute("messageSuccess", "Se bloqueó al usuario: " + user.getName() 
				+ " " + user.getPrimaryLastName());
		} else {
			user.setStatus(true);
			attribute.addFlashAttribute("messageSuccess", "Se desbloqueó al usuario: " + user.getName() 
				+ " " + user.getPrimaryLastName());
		}
		
		serviceUser.save(user);
		return "redirect:/users/";
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute User user, BindingResult result, RedirectAttributes attribute) {
		if(result.hasErrors())
			return "users/formUser";
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		serviceUser.save(user);
		attribute.addFlashAttribute("messageSuccess", "Se registró al usuario: " + user.getName().toUpperCase() 
							+ " " + user.getPrimaryLastName().toUpperCase());
		return "redirect:/users/";
	}
	
}
