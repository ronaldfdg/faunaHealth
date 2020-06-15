package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		model.addAttribute("messageInfo", "Realice una busqueda para poder visualizar la información");
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
	public String searchByPatient(@RequestParam("pageNumber") int pageNumber,
			@RequestParam(name = "nickname", required = false) String nickname, 
			@RequestParam(name = "lastName", required = false) String lastName, RedirectAttributes attribute, Model model) {
		
		Page<OperationDetail> operations = null;
		
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("operationDate").descending());
		
		if(nickname != null && lastName != null)
			operations = serviceOperationDetail.findOperationsByPatient(nickname, lastName, page);
		else if(nickname != null)
			operations = serviceOperationDetail.findOperationsByPatientName(nickname, page);
		else if(lastName != null)
			operations = serviceOperationDetail.findOperationsByPatientLastName(lastName, page);
		else {
			attribute.addFlashAttribute("messageWarning", "No puede realizar una busqueda de esa manera. Debe ingresar el nombre y/o el apellido");
			return "redirect:/operations/";
		}
		
		if(operations.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/operations/";
		}
			
		
		model.addAttribute("operations", operations);
		model.addAttribute("nickname", nickname);
		model.addAttribute("lastName", lastName);
		return "operations/listOperations";
	}
	
	@GetMapping("/searchByDates")
	public String searchByDates(@RequestParam("pageNumber") int pageNumber,
			@RequestParam(name = "startDate", required = false) Date startDate, 
			@RequestParam(name = "endDate", required = false) Date endDate, RedirectAttributes attribute, Model model) {
		
		Page<OperationDetail> operations = null;
		
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("operationDate").descending());
		
		if(startDate != null && endDate != null)
			operations = serviceOperationDetail.findOperationsBetweenDates(startDate, endDate, page);
		else if(startDate != null)
			operations = serviceOperationDetail.findOperationsByDate(startDate, page);
		else {
			attribute.addFlashAttribute("messageWarningDates", "No puede realizar una busqueda de esa manera. Estas son las combinaciones de filtro:");
			return "redirect:/operations/";
		}
		
		if(operations.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/operations/";
		}
		
		model.addAttribute("operations", operations);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "operations/listOperations";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, @RequestParam("patientId") int patientId, Model model) {
		OperationDetail operationDetail = serviceOperationDetail.findById(id);
		model.addAttribute("operationDetail", operationDetail);
		model.addAttribute("patient", servicePatient.findById(patientId));
		model.addAttribute("users", serviceUser.findAll());
		model.addAttribute("operationTypes", serviceOperation.findAll());
		
		return "operations/formOperation";
	}
	
	@GetMapping("/viewDetail/{id}")
	public String viewDetail(@PathVariable("id") int id, Model model) {
		OperationDetail operationDetail = serviceOperationDetail.findById(id);
		model.addAttribute("operationDetail", operationDetail);
		return "operations/detail";
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute OperationDetail operationDetail, BindingResult result, RedirectAttributes attribute) {
		
		if(result.hasErrors()) {
			attribute.addFlashAttribute("messageError", "Ocurrió un problema. Por favor registrar la cirugía de forma correcta.");
			return "operations/formOperation";
		}
		
		if(serviceOperationDetail.existsById(operationDetail.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la información de la cirugía");
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró una nueva cirugía");
		
		serviceOperationDetail.save(operationDetail);
		return "redirect:/operations/";
			
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
