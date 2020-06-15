package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.service.BreedService;
import com.faunahealth.web.service.ClientService;
import com.faunahealth.web.service.DistrictService;
import com.faunahealth.web.service.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private BreedService serviceBreed;

	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private DistrictService serviceDistrict;

	@Autowired
	private ClientService serviceClient;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("messageInfo", "Realice una busqueda para poder visualizar la información");
		return "patients/listPatients";
	}

	@GetMapping("/record/{id}")
	public String recordRabbit(@ModelAttribute Patient patient, @PathVariable("id") int specieId
			, @RequestParam(name = "clientId", required = false) Integer clientId, Model model) {
		
		model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(specieId));
		model.addAttribute("districts", serviceDistrict.findAll());
		
		if(clientId != null)
			patient.setClient(serviceClient.findById(clientId));
		
		return "patients/formPatient";
	}

	@PostMapping("/savePatient")
	public String saveDog(@ModelAttribute Patient patient, BindingResult result, RedirectAttributes attribute,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(patient.getBreed().getSpecie().getId()));
			model.addAttribute("districts", serviceDistrict.findAll());
			return "patients/formPatient";
		}

		if (servicePatient.existsById(patient.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la información del paciente: " + patient.getNickname() 
				+ " " + patient.getClient().getPrimaryLastName());
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró la información del nuevo paciente: " + patient.getNickname() 
				+ " " + patient.getClient().getPrimaryLastName());

		serviceClient.save(patient.getClient());
		servicePatient.save(patient);
		
		return "redirect:/patient/";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {

		Patient patient = servicePatient.findById(id);

		model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(patient.getBreed().getSpecie().getId()));
		model.addAttribute("patient", patient);
		model.addAttribute("districts", serviceDistrict.findAll());
		
		
		return "patients/formPatient";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attribute) {

		Patient patient = servicePatient.findById(id);
		servicePatient.deleteById(id);
		attribute.addFlashAttribute("messageSuccess",
				"Se eliminó al paciente: " + patient.getNickname() + " " + patient.getClient().getPrimaryLastName());
		return "redirect:/patient/";
	}

	@GetMapping("/searchByOwner")
	public String searchByOwner(@RequestParam("pageNumber") int pageNumber,
			@RequestParam("clientId") int clientId, 
			RedirectAttributes attribute, Model model) {

		if (!serviceClient.existsById(clientId)) {
			attribute.addFlashAttribute("messageWarning", "No existe ningún cliente con el código: " + clientId);
			return "redirect:/patient/";
		}

		Client client = serviceClient.findById(clientId);
		
		Pageable page = PageRequest.of(pageNumber, 10);
		Page<Patient> patients = servicePatient.findPatientsByClientAndPage(clientId, page);

		if (patients.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "El cliente " + client.getName() + " " + client.getPrimaryLastName()
			+ " no tiene una o más mascotas registradas");
			return "redirect:/patient/";
		}

		model.addAttribute("patients", patients);
		return "patients/listPatients";
	}

	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "nickname", required = false) String nickname,  
			@RequestParam(name = "primaryLastName", required = false) String primaryLastName,
			@RequestParam("pageNumber") int pageNumber,
			RedirectAttributes attribute, Model model) {
		
		Page<Patient> patients = null;
		
		Pageable page = PageRequest.of(pageNumber, 10);
		
		if(nickname != null && primaryLastName != null)
			patients =servicePatient.findPatientsByNicknameAndPrimaryLastNameAndPage(nickname, primaryLastName, page);
		else if(nickname != null)
			patients = servicePatient.findPatientsByNicknameAndPage(nickname, page);
		else if(primaryLastName != null)
			patients = servicePatient.findPatientsByPrimaryLastName(primaryLastName, page);
		else {
			attribute.addFlashAttribute("messageWarning", "No ingreso ningún valor para Alias ni Apellido. Debe ingresar por lo menos uno");
			return "redirect:/patient/";
		}
			
		if(patients.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/patient/";
		}
		
		model.addAttribute("patients", patients);
		model.addAttribute("nickname", nickname);
		model.addAttribute("primaryLastName", primaryLastName);
		
		return "patients/listPatients";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
