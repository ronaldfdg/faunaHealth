package com.faunahealth.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	@Scheduled(cron = "0 37 22 * * ?")
	void sendReminder() throws Exception {
		
		Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
		
		List<Appointment> appointments = serviceAppointment.appointmentsToday(currentDate);
		
		List<Integer> patientsIds = null;
		patientsIds = new ArrayList<>();
		
		for(Appointment appointment : appointments)
			patientsIds.add(appointment.getPatient().getId());
		
		List<Patient> patients = servicePatient.findPatientsByIds(patientsIds);
		
		Appointment appointment = null;
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		EmailMessage emailMessage = new EmailMessage();
		
		if(!patients.isEmpty()) {
			for(Patient patient : patients) {
				
				emailMessage.setFrom(from);
				emailMessage.setTo_address(patient.getClient().getEmailAddress());
				emailMessage.setSubject("Recordatorio de cita - Clínica Veterinaria Fauna Health");
				model.put("patient", patient.getNickname() + " " + patient.getClient().getPrimaryLastName());
				model.put("client", patient.getClient().getName() + " " + patient.getClient().getPrimaryLastName());
				
				appointment = serviceAppointment.findAppointmentByPatient(patient.getId());
				
				model.put("appointmentDate", dateFormat.format(appointment.getNextAppointmentDate()));
				model.put("reason", appointment.getReason());
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
