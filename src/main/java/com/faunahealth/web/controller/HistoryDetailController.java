package com.faunahealth.web.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.HistoryDetail;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.entity.Weight;
import com.faunahealth.web.service.ClinicHistoryService;
import com.faunahealth.web.service.HistoryDetailService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.service.UserService;
import com.faunahealth.web.service.WeightService;

@Controller
@RequestMapping("/historyDetails")
public class HistoryDetailController {

	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private ClinicHistoryService serviceClinicHistoryService;
	
	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private HistoryDetailService serviceHistoryDetail;
	
	@Autowired
	private WeightService serviceWeight;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@GetMapping("/{id}")
	public String index(@PathVariable("id") int id, @RequestParam("pageNumber") int pageNumber,
			@RequestParam("patient") int patientId, RedirectAttributes attribute, Model model) {
		
		Pageable page = PageRequest.of(pageNumber, 6, Sort.by("attentionDate").descending());
		
		Patient patient = servicePatient.findById(patientId);
		
		Page<HistoryDetail> attentions = serviceHistoryDetail.findHistoryDetailsByClinicHistoryAndPage(id, page);
		
		if (!attentions.isEmpty()) {
			model.addAttribute("clinicHistory", serviceClinicHistoryService.findById(id));
			model.addAttribute("weights", serviceWeight.findByPatient_Id(patientId));
		} else {
			model.addAttribute("messageInfo", "El paciente no tiene ninguna atención registrada o más atenciones registradas");	
		}
		
		model.addAttribute("patient", patient);
		model.addAttribute("attentions", attentions);
		
		return "clinicHistory/details/listAttentions";
	}

	@GetMapping("/register/{id}")
	public String register(@ModelAttribute HistoryDetail historyDetail, @PathVariable("id") int id,
			@RequestParam("patient") int patientId, Model model) throws ParseException {
		
		historyDetail.setId(0); // Lo puse debido a que se igualaba el id de la historia clinica con la atencion
		
		model.addAttribute("weight", null);
		model.addAttribute("clinicHistory", serviceClinicHistoryService.findById(id));
		model.addAttribute("patient", servicePatient.findById(patientId));
		model.addAttribute("users", serviceUser.findAll());
		
		return "clinicHistory/details/formAttention";
	}

	@GetMapping("/edit/{patientId}")
	public String edit(@PathVariable("patientId") int patientId, @RequestParam("clinicHistory") int clinicHistoryId,
			@RequestParam("date") Date date, Model model) {
		
		HistoryDetail historyDetail = serviceHistoryDetail.findHistoryPerClinicHistoryAndDate(clinicHistoryId, date);
		
		model.addAttribute("historyDetail", historyDetail);
		
		Patient patient = servicePatient.findById(patientId);
		
		model.addAttribute("patient", patient);
		
		Weight weight = serviceWeight.findByPatientAndDate(patientId, historyDetail.getAttentionDate());
		
		model.addAttribute("weight", weight);
		model.addAttribute("users", serviceUser.findAll());
		model.addAttribute("clinicHistory", serviceClinicHistoryService.findById(clinicHistoryId));
		
		return "clinicHistory/details/formAttention";
	}

	@PostMapping("/record")
	public String record(@ModelAttribute HistoryDetail historyDetail, RedirectAttributes attribute,
			@RequestParam("patient") int patientId, @RequestParam(name = "amount", required = false) Double amount) {
		
		Date date = null;
		Patient patient = servicePatient.findById(patientId);
		
		try {
			date = dateFormat.parse(dateFormat.format(historyDetail.getAttentionDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Weight weight = serviceWeight.findByPatientAndDate(patientId, date);
		
		if (weight == null && amount != null) {
			Weight newWeight = new Weight();
			newWeight.setAmount(amount);
			newWeight.setPatient(patient);
			serviceWeight.save(newWeight);
		}
		
		if (serviceHistoryDetail.existsById(historyDetail.getId()))
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la atención médica");
		else
			attribute.addFlashAttribute("messageSuccess", "Se registró una nueva atención");
		
		serviceHistoryDetail.save(historyDetail);
		attribute.addAttribute("id", patient.getId());
		
		return "redirect:/clinicHistory/{id}";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}