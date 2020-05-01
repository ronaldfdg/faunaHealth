package com.faunahealth.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		model.addAttribute("clients", serviceClient.findAll());
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
	public String searchByDNI(@RequestParam("documentNumber") String documentNumber, RedirectAttributes attribute,
			Model model) {

		List<Client> clients = new ArrayList<>();
		Client client = serviceClient.findByDocumentNumber(documentNumber);

		if (client == null) {
			attribute.addFlashAttribute("messageWarning",
					"No se encontró ningún cliente con el DNI: " + documentNumber);
			return "redirect:/clients/";
		}

		clients.add(client);
		model.addAttribute("clients", clients);
		return "clients/listClients";
	}

	@GetMapping("/searchBy")
	public String searchBy(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "primaryLastName", required = false) String primaryLastName,
			RedirectAttributes attribute, Model model) {

		List<Client> clients = null;

		if (!name.equals("") && primaryLastName.equals("")) {
			clients = serviceClient.findByNameContaining(name);
			model.addAttribute("clients", clients);
		} else if (name.equals("") && !primaryLastName.equals("")) {
			clients = serviceClient.findByPrimaryLastNameContaining(primaryLastName);
			model.addAttribute("clients", clients);
		} else if (!name.equals("") && !primaryLastName.equals("")) {
			clients = serviceClient.findByNameContainingAndPrimaryLastNameContaining(name, primaryLastName);
			model.addAttribute("clients", clients);
		}
		
		if(clients.isEmpty()) {
			attribute.addFlashAttribute("messageWarning", "No se obtuvieron resultados");
			return "redirect:/clients/";
		}

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
