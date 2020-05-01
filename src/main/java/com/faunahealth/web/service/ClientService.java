package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Client;

public interface ClientService {
	
	List<Client> findAll();
	List<Client> findByNameContaining(String name);
	List<Client> findByPrimaryLastNameContaining(String primaryLastName);
	List<Client> findByNameContainingAndPrimaryLastNameContaining(String name, String primaryLastName);
	Client findById(int id);
	Client findByDocumentNumber(String documentNumber);
	boolean existsById(int id);
	void save(Client client);
	void deleteById(int id);

}
