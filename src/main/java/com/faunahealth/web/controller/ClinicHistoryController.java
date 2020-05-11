package com.faunahealth.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.ClinicHistory;
import com.faunahealth.web.service.ClinicHistoryService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.util.Utileria;

@Controller
@RequestMapping("/clinicHistory")
public class ClinicHistoryController {

	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private ClinicHistoryService serviceClinicHistory;
	
	@GetMapping("/{id}")
	public String viewClinicHistory(@ModelAttribute ClinicHistory clinicHistory, @PathVariable("id") int id, Model model) {
		
		ClinicHistory history = serviceClinicHistory.findByPatient(id);
		
		if(history == null)
			model.addAttribute("messageInfo", "Este paciente aún no tiene un historial clínico");
		
		model.addAttribute("history", history); //Para esconder o no el div del formulario
		model.addAttribute("patient", servicePatient.findById(id));
		model.addAttribute("years", Utileria.getYears(servicePatient.findById(id).getBirthday(), new Date()));
		
		return "clinicHistory/listHistory";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute ClinicHistory clinicHistory, BindingResult result, RedirectAttributes attribute) {
		
		if(result.hasErrors())
			return "clinicHistory/listHistory";
		
		if(serviceClinicHistory.existsById(clinicHistory.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la historia clínica");
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró la historia clínica");
		
		serviceClinicHistory.save(clinicHistory);
		attribute.addAttribute("id", clinicHistory.getPatient().getId());
		
		return "redirect:/clinicHistory/{id}";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam("patientId") int id, Model model) {
		
		ClinicHistory clinicHistory = serviceClinicHistory.findByPatient(id);
		model.addAttribute("clinicHistory", clinicHistory);
		model.addAttribute("history", null);
		model.addAttribute("patient", servicePatient.findById(id));
		
		return "clinicHistory/listHistory";
	}
	
}
