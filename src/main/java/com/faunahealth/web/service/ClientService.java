package com.faunahealth.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Client;

public interface ClientService {
	
	List<Client> findAll();
	
	Page<Client> findClientsByNameAndPage(String name, Pageable page);
	List<Client> findByNameContaining(String name);
	
	Page<Client> findClientsByPrimaryLastNameAndPage(String primaryLastName, Pageable page);
	List<Client> findByPrimaryLastNameContaining(String primaryLastName);
	
	Page<Client> findClientsByNameAndPrimaryLastNameAndPage(String name, String primaryLastName, Pageable page);
	List<Client> findByNameContainingAndPrimaryLastNameContaining(String name, String primaryLastName);
	
	Page<Client> findByDocumentNumber(String documentNumber, Pageable page);
	
	Client findById(int id);
	boolean existsById(int id);
	void save(Client client);
	void deleteById(int id);

}
