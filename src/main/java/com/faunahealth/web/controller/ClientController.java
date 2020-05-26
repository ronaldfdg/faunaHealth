package com.faunahealth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.service.ClientService;
import com.faunahealth.web.service.DistrictService;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientService serviceClient;

	@Autowired
	private DistrictService serviceDistrict;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("messageInfo", "Realice una busqueda para poder visualizar la información");
		return "clients/listClients";
	}

	@GetMapping("/record")
	public String record(@ModelAttribute Client client, Model model) {
		model.addAttribute("districts", serviceDistrict.findAll());
		return "clients/formClient";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("districts", serviceDistrict.findAll());
		model.addAttribute("client", serviceClient.findById(id));
		return "clients/formClient";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, RedirectAttributes attribute) {
		Client client = serviceClient.findById(id);
		serviceClient.deleteById(id);
		attribute.addFlashAttribute("messageSuccess",
				"Se eliminó al paciente " + client.getName() + " " + client.getPrimaryLastName());
		return "redirect:/clients/";
	}

	@GetMapping("/searchByDNI")
	public String searchByDNI(@RequestParam("pageNumber") int pageNumber,
			@RequestParam("documentNumber") String documentNumber, 
			RedirectAttributes attribute,
			Model model) {

		Pageable page = PageRequest.of(pageNumber, 5);
		
		Page<Client> client = null;
		
		client = serviceClient.findByDocumentNumber(documentNumber, page);

		if (client.isEmpty()) {
			attribute.addFlashAttribute("messageWarning",
					"No se encontró ningún cliente con el DNI: " + documentNumber);
			return "redirect:/clients/";
		}
		
		model.addAttribute("clients", client);
		return "clients/listClients";
	}

	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "primaryLastName", required = false) String primaryLastName,
			@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
			RedirectAttributes attribute, Model model) {

		Page<Client> clients = null;
		
		Pageable page = PageRequest.of(pageNumber, 5);
		
		if (!name.equals("") && primaryLastName.equals("")) {
			clients = serviceClient.findClientsByNameAndPage(name, page);
		} else if (name.equals("") && !primaryLastName.equals("")) {
			clients = serviceClient.findClientsByPrimaryLastNameAndPage(primaryLastName, page);
		} else if (!name.equals("") && !primaryLastName.equals("")) {
			clients = serviceClient.findClientsByNameAndPrimaryLastNameAndPage(name, primaryLastName, page);
		} else {
			attribute.addFlashAttribute("messageWarning", "No ingreso ningún valor para Nombre ni Apellido. Debe ingresar por lo menos uno");
			return "redirect:/clients/";
		}
		
		if(clients.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se obtuvieron resultados");
			return "redirect:/clients/";
		}

		model.addAttribute("clients", clients);
		model.addAttribute("name", name);
		model.addAttribute("primaryLastName", primaryLastName);
		
		return "clients/listClients";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Client client, BindingResult result, RedirectAttributes attribute) {
		if (result.hasErrors())
			return "clients/formClient";

		if (serviceClient.existsById(client.getId()))
			attribute.addFlashAttribute("messageSuccess",
					"Se actualizó la información del paciente " + client.getName() + " " + client.getPrimaryLastName());
		else
			attribute.addFlashAttribute("messageSuccess",
					"Se registró al paciente " + client.getName() + " " + client.getPrimaryLastName());

		serviceClient.save(client);
		return "redirect:/clients/";
	}

}
