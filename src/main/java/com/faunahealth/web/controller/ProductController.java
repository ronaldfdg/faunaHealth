package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.service.ProductKindService;
import com.faunahealth.web.service.ProductService;
import com.faunahealth.web.service.ProviderService;

@Controller
@RequestMapping("/products")
public class ProductController {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private ProviderService serviceProvider;
	
	@Autowired
	private ProductKindService serviceProductKind;
	
	@Autowired
	private ProductService serviceProduct;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("productsKind", serviceProductKind.kindsOfProduct());
		model.addAttribute("messageInfo", "Realice una busqueda para poder visualizar la información");
		return "products/listProducts";
	}
	
	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "name", required = false) String name
			, @RequestParam(name = "idSpecie", required = false) String idSpecie
			, @RequestParam(name = "idProductKind", required = false) String idProductKind
			, @RequestParam("pageNumber") int pageNumber, Model model, RedirectAttributes attribute) {
		
		Pageable page = PageRequest.of(pageNumber, 5, Sort.by("name"));
		
		List<Product> products = null;
		
		if(!name.equals("")) {
			   
			if(!idSpecie.equals("") && !idProductKind.equals(""))
				products = serviceProduct.productsByKindAndSpecieAndName(Integer.parseInt(idProductKind), Integer.parseInt(idSpecie), name, page);
			else if (!idSpecie.equals(""))
				products = serviceProduct.productsBySpecieAndName(Integer.parseInt(idSpecie), name, page);
			else if (!idProductKind.equals(""))
				products = serviceProduct.productsByKindAndName(Integer.parseInt(idProductKind), name, page);
			else
				products = serviceProduct.productsByName(name, page);
			
		} else if (!idSpecie.equals("") && !idProductKind.equals("")) {
			products = serviceProduct.productsBySpecieAndKind(Integer.parseInt(idSpecie), Integer.parseInt(idProductKind), page);
		} else {
			attribute.addFlashAttribute("messageWarning", "Tiene que completar un campo, los filtros de busqueda se pueden hacer con las siguientes combinaciones: ");
			return "redirect:/products/";
		}
		
		if(products.isEmpty()) {
			attribute.addFlashAttribute("messageWarningFilter", "No se encontraron resultados");
			return "redirect:/products/";
		}
		
		model.addAttribute("products", products);
		model.addAttribute("name", name);
		model.addAttribute("idSpecie", idSpecie);
		model.addAttribute("idProductKind", idProductKind);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("productsKind", serviceProductKind.kindsOfProduct());
		
		return "products/listProducts";
	}
	
	@GetMapping("/register")
	public String register(@ModelAttribute Product product, Model model) {
		model.addAttribute("productsKind", serviceProductKind.kindsOfProduct());
		model.addAttribute("providers", serviceProvider.findAll());
		return "products/formProduct";
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute Product product, BindingResult result, Model model, 
				RedirectAttributes attribute) {
		
		if(result.hasErrors()) {
			model.addAttribute("productsKind", serviceProductKind.kindsOfProduct());
			model.addAttribute("providers", serviceProvider.findAll());
			return "products/formProduct";
		}
		
		serviceProduct.save(product);
		attribute.addFlashAttribute("messageSuccess", "Se registró el producto correctamente");
		return "redirect:/products/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
}
