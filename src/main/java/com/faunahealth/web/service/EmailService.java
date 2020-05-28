package com.faunahealth.web.service;

import com.faunahealth.web.bean.EmailMessage;

public interface EmailService {

	void sendEmail(EmailMessage emailMessage, String templateName) throws Exception;
	
}
