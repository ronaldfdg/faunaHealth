package com.faunahealth.web.service.Impl;

import org.springframework.stereotype.Service;

import com.faunahealth.web.service.WhatsAppService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

	public static final String ACCOUNT_SID = "AC698597a1155e1ed1b9e63718b7f633ff";
	public static final String AUTH_TOKEN = "6d646bfad1d2316a9b900bba7df9cad4";
	
	@Override
	public void sendReminder(String clientNumber, String appointmentMessage) {
		
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		Message message = Message
				.creator(new PhoneNumber("whatsapp:+51" + clientNumber), 
						 new PhoneNumber("whatsapp:+14155238886"), appointmentMessage)
				.create();
		
		System.out.println(message.getSid());
	}
	
}
