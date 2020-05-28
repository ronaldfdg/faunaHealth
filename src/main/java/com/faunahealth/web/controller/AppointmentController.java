package com.faunahealth.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Appointment;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.service.AppointmentService;
import com.faunahealth.web.service.PatientService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private AppointmentService serviceAppointment;
	
	@Autowired
	private PatientService servicePatient;
	
	@GetMapping("/")
	public String index(Model model) throws ParseException {
		List<Appointment> appointmentsToday = serviceAppointment.appointmentsToday(dateFormat.parse(dateFormat.format(new Date())));
		
		if(!appointmentsToday.isEmpty())
			model.addAttribute("appointments", appointmentsToday);
		else
			model.addAttribute("messageInfo", "No hay citas programadas para hoy");
		
		model.addAttribute("date", new Date());
		return "appointments/listAppointments";
	}
	
	@GetMapping("/{id}/register")
	public String register(@ModelAttribute Appointment appointment 
			, @PathVariable("id") int patientId, Model model) {
		
		Patient patient = servicePatient.findById(patientId);
		model.addAttribute("patient", patient);
		return "appointments/formAppointment";
		
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute Appointment appointment, RedirectAttributes attribute) {
		serviceAppointment.save(appointment);
		attribute.addFlashAttribute("messageSuccess", "Cita programada correctamente");
		attribute.addAttribute("id", appointment.getPatient().getId());
		return "redirect:/clinicHistory/{id}";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}
