package com.faunahealth.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Weight;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.service.WeightService;

@Controller
@RequestMapping("/weight")
public class WeightController {

	@Autowired
	private WeightService serviceWeight;
	
	@Autowired
	private PatientService servicePatient;
	
	@GetMapping("/{id}")
	public String viewWeights(@ModelAttribute Weight weight, @PathVariable("id") int id, Model model) {
		
		List<Weight> weights = serviceWeight.findByPatient_Id(id);
		
		if(weights.isEmpty())
			model.addAttribute("messageInfo", "Este paciente aún no tiene registrado ningún peso");
		
		model.addAttribute("weights", weights);
		model.addAttribute("patient", servicePatient.findById(id));
		return "weight/listWeight";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Weight weight, RedirectAttributes attribute) {
		serviceWeight.save(weight);
		attribute.addFlashAttribute("messageSuccess", "Se registró un nuevo peso");
		return "redirect:/weight/" + weight.getPatient().getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attribute) {
		
		int idPatient = serviceWeight.findById(id).getPatient().getId();
		serviceWeight.deleteById(id);
		attribute.addFlashAttribute("messageSuccess", "Se eliminó el registro del pesaje");
		return "redirect:/weight/" + idPatient;
		
	}
	
}
