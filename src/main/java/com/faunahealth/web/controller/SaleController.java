package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.service.ClientService;
import com.faunahealth.web.service.ProductService;
import com.faunahealth.web.service.SaleDetailService;
import com.faunahealth.web.service.SaleService;

@Controller
@RequestMapping("/sales")
public class SaleController {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private SaleService serviceSale;
		
	@Autowired
	private ClientService serviceClient;
	
	@Autowired
	private SaleDetailService serviceSaleDetail;
	
	@Autowired
	private ProductService serviceProduct;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("messageInfo", "Realice una busqueda para poder visualizar la información");
		return "sales/listSales";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("products", serviceProduct.findAll());
		model.addAttribute("sale", null);
		return "sales/formSale";
	}
	
	@GetMapping("/searchByClient")
	public String searchBy(@RequestParam("pageNumber") int pageNumber
			, @RequestParam("clientId") Integer clientId, Model model, 
			RedirectAttributes attribute) {
		
		Client client = serviceClient.findById(clientId);
		List<Sale> clientSales = null;
		
		if(client == null) {
			attribute.addFlashAttribute("messageWarning", "No existe un cliente con el código "+clientId);
			return "redirect:/sales/";
		}
		
		Pageable page = PageRequest.of(pageNumber, 5);
		
		clientSales = serviceSale.findByClient(clientId, page);
		
		return validateIfCustomerHasSales(pageNumber, clientId, model, attribute, client, clientSales);
		
	}

	private String validateIfCustomerHasSales(int pageNumber, Integer clientId, Model model,
			RedirectAttributes attribute, Client client, List<Sale> clientSales) {
		if(clientSales.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "El cliente " + client.getName() + " " + client.getPrimaryLastName()
					+ " no tiene 1 venta registrada o más ventas registradas");
			return "redirect:/sales/";
		} else {
			model.addAttribute("sales", clientSales);
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("clientId", clientId);
			
			return "sales/listSales";	
		}
	}
	
	@GetMapping("/searchByDates")
	public String searchByDates(@RequestParam("pageNumber") int pageNumber
			, @RequestParam(name = "startDate", required = false) Date startDate
			, @RequestParam(name = "endDate", required = false) Date endDate, Model model
			, RedirectAttributes attribute) {
		
		List<Sale> salesByDate = null;
		
		Pageable page = PageRequest.of(pageNumber, 5);
		
		if(startDate != null && endDate != null) {
			salesByDate = serviceSale.findBetweenDates(startDate, endDate, page);	
		} else {
			salesByDate = serviceSale.findByDate(startDate, page);
		}
		
		return validateIfThereAreSales(pageNumber, startDate, endDate, model, attribute, salesByDate);
		
	}

	private String validateIfThereAreSales(int pageNumber, Date startDate, Date endDate, Model model,
			RedirectAttributes attribute, List<Sale> salesByDate) {
		if(salesByDate.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se encontró una o más ventas registradas");
			return "redirect:/sales/";
		} else {
			model.addAttribute("sales", salesByDate);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("pageNumber", pageNumber);
			
			return "sales/listSales";	
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, RedirectAttributes attribute, Model model) {
		Sale sale = serviceSale.findById(id);
		if(sale == null) {
			attribute.addFlashAttribute("messageWarning", "No existe ninguna venta con el código " + id);
			return "redirect:/sales/";
		}
		model.addAttribute("sale", sale);
		model.addAttribute("products", serviceProduct.findAll());
		return "sales/formSale";
	}
	
	@GetMapping("/viewDetail/{id}")
	public String viewDetail(@PathVariable("id") int id, RedirectAttributes attribute, Model model) {
		
		Sale sale = serviceSale.findById(id);
		
		if(sale == null) {
			attribute.addFlashAttribute("messageWarning", "No existe ninguna venta con el código " + id);
			return "redirect:/sales/";
		}
		
		model.addAttribute("sale", sale);
		model.addAttribute("saleDetails", serviceSaleDetail.findBySale_Id(id));
		
		return "sales/detail";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attribute) {
		Sale sale = serviceSale.findById(id);
		serviceSaleDetail.deleteSaleDetails(sale.getSaleDetails());
		serviceSale.deleteById(sale.getId());
		attribute.addFlashAttribute("messageSuccess", "Venta eliminada");
		return "redirect:/sales/";
	}
	
	@PostMapping("/record")
	public String record(@RequestParam(name = "saleId", required = false) Integer saleId, @RequestParam("productId") Integer[] productsId, @RequestParam("amount") Integer[] amount
			, @RequestParam("clientId") Integer clientId, @RequestParam("saleDate") Date saleDate
			, @RequestParam("cash") double cash, Model model, RedirectAttributes attribute) {
		
		Client client = null;
		
		List<Product> products = serviceProduct.findByIdIn(Arrays.asList(productsId));
		
		client = serviceClient.findById(clientId);
		
		if(client == null) {
			model.addAttribute("messageError", "No existe ningún cliente con el código " + clientId);
			model.addAttribute("products", serviceProduct.findAll());
			return "sales/formSale";
		}
		
		return validateSaleDate(saleId, amount, saleDate, cash, model, attribute, client, products);
		
	}

	private String validateSaleDate(Integer saleId, Integer[] amount, Date saleDate, double cash, Model model,
			RedirectAttributes attribute, Client client, List<Product> products) {
		Sale sale = null;
		
		if(!serviceSale.validateIfSaleDateIsGreater(saleDate)) {
			if(saleId != null) {
				sale = serviceSale.processSale(saleId, client, saleDate, cash, products, amount);
				serviceSaleDetail.processSaleDetails(sale.getSaleDetails(), products, amount, sale);
				attribute.addFlashAttribute("messageSuccess", "Se actualizó la venta correctamente");
				return "redirect:/sales/";
			} else {
				sale = serviceSale.processSale(client, saleDate, cash, products, amount);
				serviceSaleDetail.processSaleDetails(products, amount, sale);
				attribute.addFlashAttribute("messageSuccess", "Se registró la venta correctamente");
				return "redirect:/sales/register";
			}
		} else {
			model.addAttribute("messageError", "No se puede registrar una venta con fecha futura");
			model.addAttribute("products", serviceProduct.findAll());
			return "sales/formSale";
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
