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
		
		if(clientSales.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "El cliente " + client.getName() + " " + client.getPrimaryLastName()
					+ " no tiene 1 venta registrada o más ventas registradas");
			return "redirect:/sales/";
		}
		
		model.addAttribute("sales", clientSales);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("clientId", clientId);
		
		return "sales/listSales";
	}
	
	@GetMapping("/searchByDates")
	public String searchByDates(@RequestParam("pageNumber") int pageNumber
			, @RequestParam(name = "startDate", required = false) Date startDate
			, @RequestParam(name = "endDate", required = false) Date endDate, Model model
			, RedirectAttributes attribute) {
		
		List<Sale> salesByDate = null;
		
		Pageable page = PageRequest.of(pageNumber, 5);
		
		if(startDate != null && endDate != null)
			salesByDate = serviceSale.findBetweenDates(startDate, endDate, page);
		else if(startDate != null) 
			salesByDate = serviceSale.findByDate(startDate, page);
		else {
			attribute.addFlashAttribute("messageWarningDates", "No puede realizar una busqueda de esa manera. Estas son las combinaciones de filtro:");
			return "redirect:/sales/";
		}
		
		if(salesByDate.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se encontró una o más ventas registradas");
			return "redirect:/sales/";
		}

		model.addAttribute("sales", salesByDate);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("pageNumber", pageNumber);
		
		return "sales/listSales";
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
	
	@PostMapping("/record")
	public String record(@RequestParam("productId") Integer[] productsId, @RequestParam("amount") Integer[] amount
			, @RequestParam("clientId") Integer clientId, @RequestParam("ticketNumber") String ticketNumber
			, @RequestParam("cash") double cash, Model model, RedirectAttributes attribute) {
		
		Client client = null;
		
		List<Product> products = serviceProduct.findByIdIn(Arrays.asList(productsId));
		
		Sale sale = null;
		
		client = serviceClient.findById(clientId);
		if(client == null) {
			model.addAttribute("messageError", "No existe ningún cliente con el código " + clientId);
			return "sales/formSale";
		} else 
			sale = serviceSale.processSale(client, ticketNumber, cash, products, amount);
		
		serviceSaleDetail.processSaleDetails(products, amount, sale);
		attribute.addFlashAttribute("messageSuccess", "Se registró la venta correctamente");
		return "redirect:/sales/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
