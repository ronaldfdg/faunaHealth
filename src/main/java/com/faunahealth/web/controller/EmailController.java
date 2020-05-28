package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.bean.EmailMessage;
import com.faunahealth.web.entity.HistoryDetail;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.entity.Weight;
import com.faunahealth.web.service.EmailService;
import com.faunahealth.web.service.HistoryDetailService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.service.WeightService;

@Controller
public class EmailController {
	
	@Value("${spring.mail.username}")
	private String from;
	
	private String templateName = "attention-template.ftl";
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private WeightService serviceWeight;
	
	@Autowired
	private HistoryDetailService serviceHistoryDetail;

	@PostMapping("/send/{idPatient}/{idAttention}")
	public String send(@PathVariable("idPatient") int idPatient,
			@PathVariable("idAttention") int idAttention,
			RedirectAttributes attribute) throws Exception {
		
		Patient patient = servicePatient.findById(idPatient);
		HistoryDetail attention = serviceHistoryDetail.findById(idAttention);
		Weight weight = serviceWeight.findByPatientAndDate(idPatient, attention.getAttentionDate());
		
		String prescription = "";
		prescription = attention.getPrescription().equals("") ? "---" : attention.getPrescription();
		
		String amountWeight = "";
		amountWeight = weight != null  ? String.valueOf(weight.getAmount()) + " kg" : "No se pesó al paciente";
		
		EmailMessage mailMessage = new EmailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo_address(patient.getClient().getEmailAddress());
		mailMessage.setSubject("Atención realizada - Clínica Veterinaria Fauna Health");
		
		/*mailMessage.setBody("Paciente: "+patient.getNickname()+" "+patient.getClient().getPrimaryLastName()
				+"\nFecha de atención: "+dateFormat.format(attention.getAttentionDate())
				+"\nMotivo: "+attention.getReason()
				+"\nDiagnóstico: "+attention.getDiagnostic()
				+"\nReceta: " +prescription
				+"\nPeso: " +amountWeight
				+"\n\n\nClinica Veterinaria Fauna Health, siempre al cuidado de su mascota.");*/
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("patient", patient.getNickname()+" "+patient.getClient().getPrimaryLastName());
		model.put("client", patient.getClient().getName() + " " + patient.getClient().getPrimaryLastName());
		model.put("attentionDate", dateFormat.format(attention.getAttentionDate()));
		model.put("reason", attention.getReason());
		model.put("diagnostic", attention.getDiagnostic());
		model.put("prescription", prescription);
		model.put("weight", amountWeight);
		model.put("byeMessage", "Fauna Health, siempre al cuidado de su mascota.");
		
		mailMessage.setModel(model);
		
		emailService.sendEmail(mailMessage, templateName);
		
		attribute.addFlashAttribute("messageSuccess", "Correo enviado correctamente");
		attribute.addAttribute("id", patient.getId());
		
		return "redirect:/clinicHistory/{id}";
	}
	
}
