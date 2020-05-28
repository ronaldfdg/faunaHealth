package com.faunahealth.web.service.Impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.faunahealth.web.bean.EmailMessage;
import com.faunahealth.web.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void sendEmail(EmailMessage emailMessage, String templateName) throws Exception {
		
		MimeMessage mailMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
		
		Template template = freemarkerConfig.getTemplate(templateName);
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailMessage.getModel());
		
		messageHelper.setTo(emailMessage.getTo_address());
		messageHelper.setSubject(emailMessage.getSubject());
		messageHelper.setText(text,true);
		
		mailSender.send(mailMessage);
		
	}

}
