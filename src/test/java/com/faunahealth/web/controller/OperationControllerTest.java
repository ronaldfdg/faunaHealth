package com.faunahealth.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.OperationDetail;

public class OperationControllerTest {

	@Test
	public void setResponseAttributesAndRedirectWhenOperationPageByInfoIsNotEmpty() {
		Page<OperationDetail> operations;
		operations = Mockito.mock(Page.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		OperationDetailController operationController = new OperationDetailController();
		String urlResponse = operationController.setResponseAttributesAndRedirectByInfo(null, null, null, model, operations);
		
		assertEquals("operations/listOperations", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectWhenOperationPageByInfoIsEmpty() {
		
		Page<OperationDetail> operations = null;
		
		RedirectAttributes flashAttribute;
		flashAttribute = Mockito.mock(RedirectAttributes.class);
		
		OperationDetailController operationController = new OperationDetailController();
		String urlResponse = operationController.setResponseAttributesAndRedirectByInfo(null, null, flashAttribute, null, operations);
		
		assertEquals("redirect:/operations/", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectWhenOperationPageByDateIsNotEmpty() {
		
		Page<OperationDetail> operations;
		operations = Mockito.mock(Page.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		OperationDetailController operationController = new OperationDetailController();
		String urlResponse = operationController.setResponseAttributesAndRedirectByDate(null, null, null, model, operations);
		
		assertEquals("operations/listOperations", urlResponse);
		
	}
	
	@Test
	public void setResponseAttributesAndRedirectWhenOperationPageByDateIsEmpty() {
		
		Page<OperationDetail> operations = null;
		
		RedirectAttributes flashAttributes;
		flashAttributes = Mockito.mock(RedirectAttributes.class);
		
		OperationDetailController operationController = new OperationDetailController();
		String urlResponse = operationController.setResponseAttributesAndRedirectByDate(null, null, flashAttributes, null, operations);
		
		assertEquals("redirect:/operations/", urlResponse);
	}
	
}
