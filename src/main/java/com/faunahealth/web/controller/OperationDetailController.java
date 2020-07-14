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
			@RequestParam(name = "lastName", required = false) String primaryLastName, RedirectAttributes attribute, Model model) {
		
		Page<OperationDetail> operations = null;
		
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("operationDate").descending());
		
		operations = serviceOperationDetail.findOperationsByPatient(nickname, primaryLastName, page);
		
		return setResponseAttributesAndRedirectByInfo(nickname, primaryLastName, attribute, model, operations);
		
	}

	protected String setResponseAttributesAndRedirectByInfo(String nickname, String lastName, RedirectAttributes attribute,
			Model model, Page<OperationDetail> operations) {
		
		if(operations != null && !operations.isEmpty()) {
			model.addAttribute("operations", operations);
			model.addAttribute("nickname", nickname);
			model.addAttribute("lastName", lastName);
			return "operations/listOperations";
		} else {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/operations/";
		}
		
	}
	
	@GetMapping("/searchByDates")
	public String searchByDates(@RequestParam("pageNumber") int pageNumber,
			@RequestParam(name = "startDate", required = false) Date startDate, 
			@RequestParam(name = "endDate", required = false) Date endDate, RedirectAttributes attribute, Model model) {
		
		Page<OperationDetail> operations = null;
		
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("operationDate").descending());
		
		operations = serviceOperationDetail.findOperationsByDate(startDate, endDate, page);
		
		return setResponseAttributesAndRedirectByDate(startDate, endDate, attribute, model, operations);
		
		
	}

	protected String setResponseAttributesAndRedirectByDate(Date startDate, Date endDate, RedirectAttributes attribute,
			Model model, Page<OperationDetail> operations) {
		if(operations != null && !operations.isEmpty()) {
			model.addAttribute("operations", operations);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			return "operations/listOperations";
		} else {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/operations/";
		}
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
