package com.faunahealth.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		
		products = serviceProduct.productsByKindAndSpecieAndName(idProductKind, idSpecie, name, page);
		
		return setResponseAttributesAndRedirectSearchBy(name, idSpecie, idProductKind, pageNumber, model, attribute,
				products);
		
	}

	protected String setResponseAttributesAndRedirectSearchBy(String name, String idSpecie, String idProductKind,
			int pageNumber, Model model, RedirectAttributes attribute, List<Product> products) {
		if(products != null && !products.isEmpty()) {
			model.addAttribute("products", products);
			model.addAttribute("name", name);
			model.addAttribute("idSpecie", idSpecie);
			model.addAttribute("idProductKind", idProductKind);
			model.addAttribute("pageNumber", pageNumber);
			return "products/listProducts";
			
		} else {
			attribute.addFlashAttribute("messageWarningFilter", "No se obtuvieron uno o más resultados");
			return "redirect:/products/";
		}
	}
	
	@GetMapping("/register")
	public String register(@ModelAttribute Product product, Model model) {
		model.addAttribute("providers", serviceProvider.findAll());
		return "products/formProduct";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Product product = serviceProduct.findById(id);
		model.addAttribute("product", product);
		model.addAttribute("providers", serviceProvider.findAll());
		return "products/formProduct";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attribute) {
		try{
			serviceProduct.deleteById(id);
			attribute.addFlashAttribute("messageSuccess", "Se eliminó el producto correctamente");
		} catch(Exception e) {
			attribute.addFlashAttribute("messageError", "No se puede eliminar el producto porque tiene ventas registradas");
		}
		
		return "redirect:/products/";
		
	}
	
	@PostMapping("/record")
	public String record(@ModelAttribute Product product, BindingResult result, Model model, 
				RedirectAttributes attribute) {
		
		if(result.hasErrors()) {
			model.addAttribute("providers", serviceProvider.findAll());
			return "products/formProduct";
		}
		
		String nextUrl = validateIfProductExists(product, attribute);
		
		serviceProduct.save(product);
		
		return nextUrl;
		
	}

	private String validateIfProductExists(Product product, RedirectAttributes attribute) {
		if(serviceProduct.existsById(product.getId())) {
			attribute.addFlashAttribute("messageSuccess", "Se actualizó la información del producto");
			return "redirect:/products/";
		} else {
			attribute.addFlashAttribute("messageSuccess", "Se registró el producto correctamente");
			return "redirect:/products/register";
		}
	}
	
	@ModelAttribute
	public void setGenerics(Model model) {
		model.addAttribute("productsKind", serviceProductKind.kindsOfProduct());
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
