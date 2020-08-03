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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Appointment;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.service.AppointmentService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.util.Utileria;

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
		List<Appointment> appointmentsToday = serviceAppointment
				.appointmentsByDate(dateFormat.parse(dateFormat.format(new Date())));
		
		setAttribute(model, appointmentsToday);
		
		model.addAttribute("date", dateFormat.format(new Date()));
		model.addAttribute("nextDaysFromToday", Utileria.getNextDays());
		
		return "appointments/listAppointments";
	}

	protected void setAttribute(Model model, List<Appointment> appointmentsToday) {
		if (!appointmentsToday.isEmpty())
			model.addAttribute("appointments", appointmentsToday);
		else
			model.addAttribute("messageWarning", "No hay citas programadas");
	}
	
	@GetMapping("/searchBy")
	public String searchBy(@RequestParam("searchDate") String searchDate, Model model) 
		throws ParseException {
		
		List<Appointment> appointments = serviceAppointment.appointmentsByDate(dateFormat.parse(searchDate));
		
		setAttribute(model, appointments);
		
		model.addAttribute("nextDaysFromToday", Utileria.getNextDays());
		model.addAttribute("date", searchDate);
		
		return "appointments/listAppointments";
	}

	@GetMapping("/{id}/register")
	public String register(@ModelAttribute Appointment appointment, @PathVariable("id") int patientId, Model model) {
		Patient patient = servicePatient.findById(patientId);
		model.addAttribute("patient", patient);
		return "appointments/formAppointment";
	}

	@GetMapping("/confirm/{id}")
	public String confirmation(@PathVariable("id") int id, RedirectAttributes attribute) {
		Appointment appointment = serviceAppointment.findById(id);
		
		confirmationAppointment(attribute, appointment);
		
		attribute.addFlashAttribute("subject", "Confirmación de asistencia");
		return "redirect:/confirmMessage";
	}

	protected void confirmationAppointment(RedirectAttributes attribute, Appointment appointment) {
		if (!appointment.isConfirmation()) {
			appointment.setConfirmation(true);
			serviceAppointment.save(appointment);
			attribute.addFlashAttribute("body",
					"Estimado cliente, acaba de confirmar la asistencia de su mascota "
							+ appointment.getPatient().getNickname() + " para la cita programa el día "
							+ dateFormat.format(appointment.getNextAppointmentDate()) + "\n¡Los esperamos!");
		} else {
			attribute.addFlashAttribute("body",
					"Estimado cliente, usted ya realizó la confirmación de asistencia de su mascota "
							+ appointment.getPatient().getNickname() + "\n¡Los esperemos!");
		}
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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}