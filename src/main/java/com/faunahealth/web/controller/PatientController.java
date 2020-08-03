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

import com.faunahealth.web.entity.Breed;
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
		
		boolean newClient = true;
		
		model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(specieId));
		model.addAttribute("districts", serviceDistrict.findAll());
		
		if(clientId != null) {
			patient.setClient(serviceClient.findById(clientId));
			newClient = false;
		}
		
		model.addAttribute("newClient", newClient);
		
		return "patients/formPatient";
	}

	@PostMapping("/savePatient")
	public String saveDog(@ModelAttribute Patient patient, BindingResult result, RedirectAttributes attribute,
			Model model) throws Exception { /**/

		Breed breed = null;
		
		if (result.hasErrors()) {
			breed = serviceBreed.findById(patient.getBreed().getId());
			model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(breed.getSpecie().getId()));
			model.addAttribute("districts", serviceDistrict.findAll());
			return "patients/formPatient";
		}
		
		if(!serviceClient.existsById(patient.getClient().getId())) {
			try {
				serviceClient.save(patient.getClient());
			} catch(Exception e) {
				breed = serviceBreed.findById(patient.getBreed().getId());
				model.addAttribute("breeds", serviceBreed.getBreedsBySpecie(breed.getSpecie().getId()));
				model.addAttribute("districts", serviceDistrict.findAll());
				validateIfDniOrEmailDuplicated(patient.getClient(), model);
				return "patients/formPatient";
			}
		}

		validateIfPatientExists(patient, attribute);

		servicePatient.save(patient);
		
		return "redirect:/patient/";
	}

	private void validateIfPatientExists(Patient patient, RedirectAttributes attribute) {
		if (servicePatient.existsById(patient.getId())) {
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la información del paciente: " + patient.getNickname() 
			+ " " + patient.getClient().getPrimaryLastName());
		}
		else {
			attribute.addFlashAttribute("messageSuccess", "Se registró la información del nuevo paciente: " + patient.getNickname() 
			+ " " + patient.getClient().getPrimaryLastName());
		}
	}
	
	private void validateIfDniOrEmailDuplicated(Client client, Model model) {
		Client validateClient = serviceClient.findByDocumentNumberLike(client.getDocumentNumber());
		if(validateClient != null) {
			model.addAttribute("messageError", "Ya existe registrado un DNI con el número: "+client.getDocumentNumber());
		} else {
			model.addAttribute("messageError", "Ya existe un cliente registrado con el correo: "+client.getEmailAddress());
		}
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
		try {
			servicePatient.deleteById(id);
			attribute.addFlashAttribute("messageSuccess",
					"Se eliminó al paciente: " + patient.getNickname() + " " + patient.getClient().getPrimaryLastName());
		} catch(Exception e) {
			attribute.addFlashAttribute("messageWarning", "No se puede eliminar al paciente porque tiene un historial clínico");
		}
		
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
		
		Page<Patient> patients = null;
		
		Pageable page = PageRequest.of(pageNumber, 10);
		
		patients = servicePatient.findPatientsByClientAndPage(clientId, page);

		return setResponseAttributesAndRedirectByOwner(attribute, model, client, patients);
		
	}

	protected String setResponseAttributesAndRedirectByOwner(RedirectAttributes attribute, Model model, Client client,
			Page<Patient> patients) {
		if (patients != null && !patients.isEmpty()) {
			model.addAttribute("patients", patients);
			return "patients/listPatients";
		} else {
			attribute.addFlashAttribute("messageWarning", "El cliente " + client.getName() + " " + client.getPrimaryLastName()
			+ " no tiene una o más mascotas registradas");
			return "redirect:/patient/";
		}
	}

	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "nickname", required = false) String nickname,  
			@RequestParam(name = "primaryLastName", required = false) String primaryLastName,
			@RequestParam("pageNumber") int pageNumber,
			RedirectAttributes attribute, Model model) {
		
		Page<Patient> patients = null;
		
		Pageable page = PageRequest.of(pageNumber, 10);
		
		patients = servicePatient.findPatientsByNicknameAndPrimaryLastNameAndPage(nickname, primaryLastName, page);
		
		return setResponseAttributesAndRedirectByInfo(nickname, primaryLastName, attribute, model, patients);
		
	}

	protected String setResponseAttributesAndRedirectByInfo(String nickname, String primaryLastName,
			RedirectAttributes attribute, Model model, Page<Patient> patients) {
		
		if(patients != null && !patients.isEmpty()) {
			model.addAttribute("patients", patients);
			model.addAttribute("nickname", nickname);
			model.addAttribute("primaryLastName", primaryLastName);
			return "patients/listPatients";
		} else {
			attribute.addFlashAttribute("messageWarning", "No se encontraron uno o más resultados");
			return "redirect:/patient/";
		}
		
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
