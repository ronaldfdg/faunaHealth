package com.faunahealth.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.faunahealth.web.bean.EmailMessage;
import com.faunahealth.web.entity.Appointment;
import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.service.AppointmentService;
import com.faunahealth.web.service.EmailService;
import com.faunahealth.web.service.PatientService;
import com.faunahealth.web.util.Utileria;

@SpringBootApplication
public class FaunaHealthApplication {
	
	private String templateName = "reminder-email.ftl";
	
	@Autowired
	private AppointmentService serviceAppointment;
	
	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private EmailService serviceMail;
	
	@Value("${spring.mail.username}")
	private String from;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static void main(String[] args) {
		SpringApplication.run(FaunaHealthApplication.class, args);
	}
	
	@Scheduled(cron = "0 0/3 20 * * ?")
	void sendReminderEmailAddress() throws Exception {
		List<Appointment> appointments = serviceAppointment.appointmentsByDate(Utileria.getTomorrowDate());
																											 
		List<Patient> patients = null;
		
		EmailMessage emailMessage = null;
		Map<String, Object> model = null;
		
		if(!appointments.isEmpty()) {
			
			List<Integer> patientsIds = new ArrayList<>(); // Para almacenar los codigos de los pacientes que tienen citas
			for (Appointment appointment : appointments) {
				if(!appointment.isConfirmation())
					patientsIds.add(appointment.getPatient().getId());
			}
				
			
			patients = servicePatient.findPatientsByIds(patientsIds); // Obtengo toda la info de los pacientes
																					// junto con la de sus dueños
			
			emailMessage = new EmailMessage();
			model = new HashMap<String, Object>();
			
			for (Patient patient : patients) {
				Appointment appointment = null;
				
				emailMessage.setFrom(from);
				emailMessage.setTo_address(patient.getClient().getEmailAddress());
				emailMessage.setSubject("\"Fauna Health\" - Recordatorio de cita");
				model.put("patient", patient);
				model.put("client", patient.getClient());
				appointment = serviceAppointment.findAppointmentByPatient(patient.getId(), Utileria.getTomorrowDate());
				model.put("appointmentDate", dateFormat.format(appointment.getNextAppointmentDate()));
				model.put("appointment", appointment);
				model.put("byeMessage", "Fauna Health, siempre al cuidado de su mascota.");
				emailMessage.setModel(model);
				serviceMail.sendEmail(emailMessage, templateName);
				
			}
		}

	}

	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
	class SchedulingConfiguration {
	}
}