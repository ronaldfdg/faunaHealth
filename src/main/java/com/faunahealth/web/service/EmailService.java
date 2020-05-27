package com.faunahealth.web.service;

import com.faunahealth.web.entity.EmailMessage;

public interface EmailService {

	void sendEmail(EmailMessage emailMessage) throws Exception;
	
}
