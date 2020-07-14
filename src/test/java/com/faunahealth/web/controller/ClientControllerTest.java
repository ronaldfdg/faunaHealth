package com.faunahealth.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Client;

public class ClientControllerTest {

	@Test
	public void setResponseAttributesAndRedirectByInfoWhenClientPageIsNotEmpty() {
		
		Page<Client> clients;
		clients = Mockito.mock(Page.class);
		
		Mockito.when(clients.isEmpty()).thenReturn(false);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		ClientController clientController = new ClientController();
		String urlResponse = clientController.setResponseAttributesAndRedirectByInfo(null, null, null, model, clients);
		
		assertEquals("clients/listClients", urlResponse);
		
	}
	
	@Test
	public void setResponseAttributesAndRedirectByInfoWhenClientPageIsEmpty() {
		
		Page<Client> clients = null;
		
		RedirectAttributes flashAttributes;
		flashAttributes = Mockito.mock(RedirectAttributes.class);
		
		ClientController clientController = new ClientController();
		String urlResponse = clientController.setResponseAttributesAndRedirectByInfo(null, null, flashAttributes, null, clients);
		
		assertEquals("redirect:/clients/", urlResponse);
		
	}
	
	@Test
	public void setResponseAttributesAndRedirectByDNIWhenClientPageIsNotEmpty() {
		
		Page<Client> client;
		client = Mockito.mock(Page.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		ClientController clientController = new ClientController();
		String urlResponse = clientController.setResponseAttributesAndRedirectByDNI(null, null, model, client);
		
		assertEquals("clients/listClients", urlResponse);
		
	}
	
	@Test
	public void setResponseAttributesAndRedirectByDNIWhenClientPageIsEmpty() {
		
		Page<Client> client = null;
		
		RedirectAttributes flashAttributes;
		flashAttributes = Mockito.mock(RedirectAttributes.class);
		
		ClientController clientController = new ClientController();
		String urlResponse = clientController.setResponseAttributesAndRedirectByDNI(null, flashAttributes, null, client);
		
		assertEquals("redirect:/clients/", urlResponse);
		
	}
	
}
