package com.faunahealth.web.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.faunahealth.web.service.WhatsAppService;
import com.faunahealth.web.util.Utileria;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class SchedulingConfiguration {
	
	private String templateName = "reminder-email.ftl";
	
	@Autowired
	private AppointmentService serviceAppointment;
	
	@Autowired
	private PatientService servicePatient;
	
	@Autowired
	private WhatsAppService serviceWhatsApp;
	
	@Autowired
	private EmailService serviceMail;
	
	@Value("${spring.mail.username}")
	private String from;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Scheduled(cron = "0 50-52 01 * * ?")
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
			
			patients = servicePatient.findPatientsByIds(patientsIds); 
			
			emailMessage = new EmailMessage();
			model = new HashMap<String, Object>();
			
			for (Patient patient : patients) {
				if(patient.getClient().isStatus()) {
					Appointment appointment = null;
					
					emailMessage.setFrom(from);
					emailMessage.setTo_address(patient.getClient().getEmailAddress());
					emailMessage.setSubject("\"Fauna Health\" - Recordatorio de cita");
					model.put("patient", patient);
					model.put("client", patient.getClient());
					appointment = serviceAppointment.findAppointmentByPatient(patient.getId(), Utileria.getTomorrowDate());
					model.put("appointmentDate", dateFormat.format(appointment.getNextAppointmentDate()));
					model.put("appointment", appointment);
					model.put("warning", "Nota: Esta cuenta de correo electrónico solo es utilizada para envíar esta notificación; "
							+ "le agradeceremos no responder con consultas personales.");
					
					model.put("byeMessage", "Fauna Health, siempre al cuidado de su mascota.");
					
					emailMessage.setModel(model);
					serviceMail.sendEmail(emailMessage, templateName);	
				}
			}
			
		}

	}
	
	@Scheduled(cron = "0 53-55 01 * * ?")
	void sendReminderWhatsApp() throws Exception {
		
		List<Appointment> appointments = serviceAppointment.appointmentsByDate(Utileria.getTomorrowDate());
		List<Patient> patients = null;
		String[] messages = new String[2];
		
		if(!appointments.isEmpty()) {
			
			List<Integer> patientsIds = new ArrayList<>();
			
			for (Appointment appointment : appointments) {
				if(!appointment.isConfirmation())
					patientsIds.add(appointment.getPatient().getId());
			}
				
			
			patients = servicePatient.findPatientsByIds(patientsIds);
			
			for (Patient patient : patients) {
				if(patient.getClient().isStatus()) {
					Appointment appointment = null;
					String appointmentMessage = null;
					String confirmationMessage = null;
					
					appointment = serviceAppointment.findAppointmentByPatient(patient.getId(), Utileria.getTomorrowDate());
					
					appointmentMessage = "Estimado " + patient.getClient().getName() + ", su mascota tiene una cita programada para el día "
							+ dateFormat.format(appointment.getNextAppointmentDate()) + ".";
					
					confirmationMessage = "Por favor revisar su correo para confirmar su asistencia.";
					
					messages[0] = appointmentMessage;
					messages[1] = confirmationMessage;
					
					for(int i = 0; i < 2; i++) {
						serviceWhatsApp.sendReminder(patient.getClient().getCellPhone(), messages[i]);
					}
				}
			}
			
		}

	}
}
