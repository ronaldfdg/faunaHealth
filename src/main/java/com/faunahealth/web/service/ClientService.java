package com.faunahealth.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Client;

public interface ClientService {
	
	Page<Client> findClientsByPage(String name, String primaryLastName, Pageable page);
	
	Page<Client> findByDocumentNumber(String documentNumber, Pageable page);
	
	Client findById(int id);
	boolean existsById(int id);
	void save(Client client);
	void deleteById(int id);

}
