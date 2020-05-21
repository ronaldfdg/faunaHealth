package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.OperationDetail;
import com.faunahealth.web.service.OperationDetailService;
import com.faunahealth.web.service.OperationService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.service.UserService;

@Controller
@RequestMapping("/operations")
public class OperationDetailController {

	@Autowired
	private OperationDetailService serviceOperationDetail;
	
	@Autowired
	private OperationService serviceOperation;
	
	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private UserService serviceUser;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("operations", serviceOperationDetail.findAll());
		return "operations/listOperations";
	}
	
	@GetMapping("/register/{id}")
	public String register(@ModelAttribute OperationDetail operationDetail, @PathVariable("id") int id, Model model) {
		
		model.addAttribute("patient", servicePatient.findById(id));
		model.addAttribute("users", serviceUser.findAll());
		model.addAttribute("operationTypes", serviceOperation.findAll());
		return "operations/formOperation";
	}
	
	@GetMapping("/searchByPatient")
	public String searchByPatient(@RequestParam(name = "nickname", required = false) String nickname, 
			@RequestParam(name = "lastName", required = false) String lastName, RedirectAttributes attribute, Model model) {
		
		List<OperationDetail> operations = null;
		
		if(!nickname.equals("") && !lastName.equals(""))
			operations = serviceOperationDetail.findOperationsByPatient(nickname, lastName);
		else if(!nickname.equals("") && lastName.equals(""))
			operations = serviceOperationDetail.findOperationsByPatientName(nickname);
		else if(nickname.equals("") && !lastName.equals(""))
			operations = serviceOperationDetail.findOperationsByPatientLastName(lastName);
		else {
			attribute.addFlashAttribute("messageInfo", "Debe ingresar el nombre y/o el apellido!");
			return "redirect:/operations/";
		}
		
		if(operations.isEmpty())
			model.addAttribute("messageInfo", "No se encontraron resultados con el nombre/apodo" + 
					nickname == "" ? "" : nickname + " y el apellido " + lastName == "" ? "" : lastName);
		
		model.addAttribute("operations", operations);
		return "operations/listOperations";
	}
	
	@GetMapping("/searchByDates")
	public String searchByDates(@RequestParam(name = "startDate", required = false) Date startDate, 
			@RequestParam(name = "endDate", required = false) Date endDate, RedirectAttributes attribute, Model model) {
		
		List<OperationDetail> operations = null;
		
		if(startDate != null && endDate == null)
			operations = serviceOperationDetail.findOperationsByDate(startDate);
		else if(startDate != null && endDate != null)
			operations = serviceOperationDetail.findOperationsBetweenDates(startDate, endDate);
		else {
			attribute.addFlashAttribute("messageInfo", "Debe igresar una fecha exacta o un rango de fechas!");
			return "redirect:/operations/";
		}
		
		model.addAttribute("operations", operations);
		return "operations/listOperations";
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute OperationDetail operationDetail, BindingResult result, RedirectAttributes attribute) {
		
		if(result.hasErrors()) {
			attribute.addFlashAttribute("messageError", "Ocurrió un problema. Por favor registrar la cirugía de forma correcta.");
			return "operations/formOperation";
		}
		
		serviceOperationDetail.save(operationDetail);
		attribute.addFlashAttribute("messageSuccess", "Se registró una nueva cirugía");
		return "redirect:/operations/";
			
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
