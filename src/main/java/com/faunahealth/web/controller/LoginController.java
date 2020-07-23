package com.faunahealth.web.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.User;
import com.faunahealth.web.service.UserService;
import com.faunahealth.web.util.Utileria;

@Controller
public class LoginController {
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/login";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("username") String username, @RequestParam("password") String password
			, RedirectAttributes attribute) {
		
		User user = serviceUser.findByUsername(username);
		
		return validateCurrentPasswordIsNotSameAsLastPassword(password, attribute, user);
		
	}

	private String validateCurrentPasswordIsNotSameAsLastPassword(String password, RedirectAttributes attribute,
			User user) {
		if(passwordEncoder.matches(password, user.getPassword())) {
			attribute.addFlashAttribute("user", user);
			attribute.addFlashAttribute("messageWarning", "No puede volver a ingresar la contraseña actual");
			return "redirect:/changePassword";
			
		} else {
			user.setPassword(passwordEncoder.encode(password));
			
			try {
				user.setExpirationDate(Utileria.addThreeMonths(user.getExpirationDate()));
			} catch(ParseException e) {
				System.out.println(e.getMessage());
			}
			
			serviceUser.save(user);
			attribute.addFlashAttribute("messageInfo", "Contraseña actualizada. Ya puede autenticarse nuevamente");
			return "redirect:/login";
		}
	}
	
}
