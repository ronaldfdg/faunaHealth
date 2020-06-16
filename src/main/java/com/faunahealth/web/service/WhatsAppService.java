package com.faunahealth.web.service;

public interface WhatsAppService {

	void sendReminder(String clientNumber, String appointmentMessage);
	
}
