package com.faunahealth.web.entity;

import java.util.List;
import java.util.Map;

public class EmailMessage {

	private String to_address;
	private String from;
	private String subject;
	private String contentType;
	private List<Object> attachments;
	private Map<String, Object> model;
	
	public EmailMessage() {
		this.contentType = "text/plain";
	}
	
	public String getTo_address() {
		return to_address;
	}
	
	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "EmailMessage [to_address=" + to_address + ", from=" + from + ", subject=" + subject + ", contentType="
				+ contentType + ", attachments=" + attachments + ", model=" + model + "]";
	}
	
}
