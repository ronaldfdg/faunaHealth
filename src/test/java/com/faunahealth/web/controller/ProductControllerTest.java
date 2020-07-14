package com.faunahealth.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Product;

public class ProductControllerTest {

	@Test
	public void setResponseAttributesAndRedirectSearchByWhenProductListIsNotEmpty() {
		List<Product> products;
		products = Mockito.mock(List.class);
		
		Model model;
		model = Mockito.mock(Model.class);
		
		ProductController productController = new ProductController();
		String urlResponse = productController.setResponseAttributesAndRedirectSearchBy(null, null, null, 0, model, null, products);
		
		assertEquals("products/listProducts", urlResponse);
	}
	
	@Test
	public void setResponseAttributesAndRedirectSearchByWhenProductListIsEmpty() {
		
		List<Product> products = null;
		
		RedirectAttributes flashAttribute;
		flashAttribute = Mockito.mock(RedirectAttributes.class);
		
		ProductController productController = new ProductController();
		String urlResponse = productController.setResponseAttributesAndRedirectSearchBy(null, null, null, 0, null, flashAttribute, products);
		
		assertEquals("redirect:/products/", urlResponse);
	}
	
}
