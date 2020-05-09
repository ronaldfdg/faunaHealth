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

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.service.BreedService;
import com.faunahealth.web.service.ClientService;
import com.faunahealth.web.service.DistrictService;
import com.faunahealth.web.service.PatientService;

@Controller
@RequestMapping("patient")
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
		model.addAttribute("patients", servicePatient.getPatients());
		return "patients/listPatients";
	}

	@GetMapping("/record/dog")
	public String recordDog(@ModelAttribute Patient patient, @RequestParam(name = "clientId", required = false) Integer clientId, Model model) {
		model.addAttribute("breeds", serviceBreed.getBreedsDog());
		model.addAttribute("districts", serviceDistrict.findAll());
		
		if(clientId != null)
			patient.setClient(serviceClient.findById(clientId));
		
		return "patients/dog/formDog";
	}
	
	@GetMapping("/record/cat")
	public String recordCat(@ModelAttribute Patient patient, @RequestParam(name = "clientId", required = false) Integer clientId, Model model) {
		model.addAttribute("breeds", serviceBreed.getBreedsCat());
		model.addAttribute("districts", serviceDistrict.findAll());
		
		if(clientId != null)
			patient.setClient(serviceClient.findById(clientId));
		
		return "patients/cat/formCat";
	}

	@GetMapping("/record/rabbit")
	public String recordRabbit(@ModelAttribute Patient patient, @RequestParam(name = "clientId", required = false) Integer clientId, Model model) {
		model.addAttribute("breeds", serviceBreed.getBreedsRabbit());
		model.addAttribute("districts", serviceDistrict.findAll());
		
		if(clientId != null)
			patient.setClient(serviceClient.findById(clientId));
		
		return "patients/rabbit/formRabbit";
	}

	@PostMapping("/savePatient")
	public String saveDog(@ModelAttribute Patient patient, BindingResult result, RedirectAttributes attribute,
			Model model) {

		if (result.hasErrors()) {
			
			switch(patient.getBreed().getSpecie().getId()) {
				case 1:
					model.addAttribute("breeds", serviceBreed.getBreedsDog());
					break;
				case 2:
					model.addAttribute("breeds", serviceBreed.getBreedsCat());
					break;
				case 3:
					model.addAttribute("breeds", serviceBreed.getBreedsRabbit());
					break;
			}
			
			model.addAttribute("districts", serviceDistrict.findAll());
			return "patients/dog/formDog";
		}

		if (servicePatient.existsById(patient.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó al paciente: " + patient.getNickname() 
				+ " " + patient.getClient().getPrimaryLastName());
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró al paciente: " + patient.getNickname() 
				+ " " + patient.getClient().getPrimaryLastName());

		serviceClient.save(patient.getClient());
		servicePatient.save(patient);
		
		return "redirect:/patient/";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {

		Patient patient = servicePatient.findById(id);

		String page = null;

		if (patient.getBreed().getSpecie().getId() == 1) {
			model.addAttribute("breeds", serviceBreed.getBreedsDog());
			page = "patients/dog/formDog";
		} else if (patient.getBreed().getSpecie().getId() == 2) {
			model.addAttribute("breeds", serviceBreed.getBreedsCat());
			page = "patients/cat/formCat";
		} else {
			model.addAttribute("breeds", serviceBreed.getBreedsRabbit());
			page = "patients/rabbit/formRabbit";
		}

		model.addAttribute("patient", patient);
		model.addAttribute("districts", serviceDistrict.findAll());
		return page;
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
	public String searchByOwner(@RequestParam("clientId") int clientId, RedirectAttributes attribute, Model model) {

		if (!serviceClient.existsById(clientId)) {
			attribute.addFlashAttribute("messageWarning", "No existe ningún cliente con el código: " + clientId);
			return "redirect:/patient/";
		}

		Client client = serviceClient.findById(clientId);

		List<Patient> patients = servicePatient.getPatientsByClient(clientId);

		if (patients.size() == 0)
			model.addAttribute("messageInfo", "El cliente " + client.getName() + " " + client.getPrimaryLastName()
					+ " no tiene ninguna mascota registrada");

		model.addAttribute("patients", patients);
		return "patients/listPatients";
	}

	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "nickname", required = false) String nickname,  
			@RequestParam(name = "primaryLastName", required = false) String primaryLastName, RedirectAttributes attribute, Model model) {
		
		List<Patient> patients = null;
		
		if(!nickname.equals("") && !primaryLastName.equals(""))
			patients =servicePatient.getPatientsByNicknameAndLastNameClient(nickname, primaryLastName);
		else if(!nickname.equals("") && primaryLastName.equals(""))
			patients = servicePatient.findByNicknameContaining(nickname);
		else if(nickname.equals("") && !primaryLastName.equals(""))
			patients = servicePatient.findByClient_PrimaryLastNameContaining(primaryLastName);
		else
			patients = servicePatient.getPatients();
		
		if(patients.isEmpty()) {
			attribute.addFlashAttribute("messageInfo", "No se encontraron resultados con el Alias: "+nickname+" y el Apellido: "+primaryLastName);
			return "redirect:/patient/";
		}
		
		model.addAttribute("patients", patients);
		return "patients/listPatients";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
		model.addAttribute("patient", servicePatient.findById(id));
		return "patients/viewPatient";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
