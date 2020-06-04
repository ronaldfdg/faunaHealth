package com.faunahealth.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.service.ClientService;
import com.faunahealth.web.service.ProductService;
import com.faunahealth.web.service.SaleDetailService;
import com.faunahealth.web.service.SaleService;

@Controller
@RequestMapping("/sales")
public class SaleController {

	@Autowired
	private SaleService serviceSale;
		
	@Autowired
	private ClientService serviceClient;
	
	@Autowired
	private SaleDetailService serviceSaleDetail;
	
	@Autowired
	private ProductService serviceProduct;
	
	@GetMapping("/")
	public String index() {
		return "";
	}
	
	@GetMapping("/register")
	public String register() {
		return "sales/formSale";
	}
	
	@PostMapping("/record")
	public String record(@RequestParam("productId") Integer[] productsId, @RequestParam("amount") String[] amount
			, RedirectAttributes attribute) {
		
		double change = 0;
		double total = 0;
		
		List<Product> products = serviceProduct.findByIdIn(Arrays.asList(productsId));
		
		Sale sale = new Sale();
		
		for(Product product : products) {
			int i = 0;
			//total += (product.getSalePrice()*amount[i]);
		}
		return "";
	}
	
}
