package com.faunahealth.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.User;
import com.faunahealth.web.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService serviceUser;

	@GetMapping("/")
	public String goHome(Authentication authentication, HttpSession session, HttpServletRequest request, RedirectAttributes attribute) {		
		User user = null;
		user = serviceUser.findByUsername(authentication.getName());
		
		if(!serviceUser.validateExpirationDate(new Date(), user.getExpirationDate())) {
			if(session.getAttribute("user") == null) {
				user.setPassword(null);
				session.setAttribute("user", user);
			}
			return "home";
		} else {
			SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
			logoutHandler.logout(request, null, null);
			attribute.addFlashAttribute("messageWarning", "Contraseña caducada. Por favor ingrese una nueva contraseña");
			attribute.addFlashAttribute("user", user);
			return "redirect:/changePassword";
		}
		
	}
	
	@GetMapping("/changePassword")
	public String changePassword() {
		return "users/changePassword";
	}

	@GetMapping("/example")
	public String example() {
		return "example";
	}

	@GetMapping("/confirmMessage")
	public String confirmMessage() {
		return "confirmMessage";
	}
	
}