package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Provider;
import com.faunahealth.web.service.DistrictService;
import com.faunahealth.web.service.ProviderService;

@Controller
@RequestMapping("/providers")
public class ProviderController {

	@Autowired
	private ProviderService serviceProvider;
	
	@Autowired
	private DistrictService serviceDistrict;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("providers", serviceProvider.findAll());
		return "providers/listProviders";
	}
	
	@GetMapping("/register")
	public String register(@ModelAttribute Provider provider, Model model) {
		model.addAttribute("districts", serviceDistrict.findAll());
		return "providers/formProvider";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Provider provider = serviceProvider.findById(id);
		model.addAttribute("provider", provider);
		model.addAttribute("districts", serviceDistrict.findAll());
		return "providers/formProvider";
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute Provider provider, BindingResult result, RedirectAttributes attribute,
				Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("districts", serviceDistrict.findAll());
			model.addAttribute("messageError", "Ocurrió un problema!");
			return "providers/formProvider";
		}
		
		if(serviceProvider.existsById(provider.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó el proveedor correctamente");
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró el proveedor correctamente");
		
		serviceProvider.save(provider);
		return "redirect:/providers/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}
