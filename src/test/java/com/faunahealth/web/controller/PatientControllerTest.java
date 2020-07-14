package com.faunahealth.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Patient;

public class PatientControllerTest {

	@Test
	public void setResponseAttributesAndRedirectByInfoWhenPatientPageIsNotEmpty() {
		Page<Patient> patients;
		patients = Mockito.mock(Page.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		PatientController patientController = new PatientController();
		String urlResponse = patientController.setResponseAttributesAndRedirectByInfo(null, null, null, model, patients);
		
		assertEquals("patients/listPatients", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectByInfoWhenPatientPageIsEmpty() {
		
		Page<Patient> patients = null;
		
		RedirectAttributes flashAttribute;
		flashAttribute = Mockito.mock(RedirectAttributes.class);
		
		PatientController patientController = new PatientController();
		String urlResponse = patientController.setResponseAttributesAndRedirectByInfo(null, null, flashAttribute, null, patients);
		
		assertEquals("redirect:/patient/", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectByOwnerWhenPatientPageIsNotEmpty() {
		Page<Patient> patients;
		patients = Mockito.mock(Page.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		PatientController patientController = new PatientController();
		String urlResponse = patientController.setResponseAttributesAndRedirectByOwner(null, model, null, patients);
		
		assertEquals("patients/listPatients", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectByOwnerWhenPatientPageIsEmpty() {
		Page<Patient> patients = null;
		
		RedirectAttributes flashAttribute;
		flashAttribute = Mockito.mock(RedirectAttributes.class);
		
		Client client;
		client = Mockito.mock(Client.class);
		
		PatientController patientController = new PatientController();
		String urlResponse = patientController.setResponseAttributesAndRedirectByOwner(flashAttribute, null, client, patients);
		
		assertEquals("redirect:/patient/", urlResponse);
	}
	
}
