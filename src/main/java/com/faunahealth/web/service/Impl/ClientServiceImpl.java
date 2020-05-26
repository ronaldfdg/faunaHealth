package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.repository.ClientRepository;
import com.faunahealth.web.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository repositoryClient;
	
	@Override
	public List<Client> findAll() {
		return repositoryClient.findAll();
	}

	@Override
	public void save(Client client) {
		repositoryClient.save(client);
	}

	@Override
	public void deleteById(int id) {
		repositoryClient.deleteById(id);
	}

	@Override
	public Client findById(int id) {
		Optional<Client> optional = repositoryClient.findById(id);
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public boolean existsById(int id) {
		return repositoryClient.existsById(id);
	}

	@Override
	public Page<Client> findByDocumentNumber(String documentNumber, Pageable page) {
		return repositoryClient.findByDocumentNumber(documentNumber, page);
	}

	@Override
	public List<Client> findByNameContaining(String name) {
		return repositoryClient.findByNameContaining(name);
	}

	@Override
	public List<Client> findByPrimaryLastNameContaining(String primaryLastName) {
		return repositoryClient.findByPrimaryLastNameContaining(primaryLastName);
	}

	@Override
	public List<Client> findByNameContainingAndPrimaryLastNameContaining(String name, String primaryLastName) {
		return repositoryClient.findByNameContainingAndPrimaryLastNameContaining(name, primaryLastName);
	}

	@Override
	public Page<Client> findClientsByNameAndPage(String name, Pageable page) {
		return repositoryClient.findClientsByNameAndPage(name, page);
	}

	@Override
	public Page<Client> findClientsByPrimaryLastNameAndPage(String primaryLastName, Pageable page) {
		return repositoryClient.findClientsByPrimaryLastNameAndPage(primaryLastName, page);
	}

	@Override
	public Page<Client> findClientsByNameAndPrimaryLastNameAndPage(String name, String primaryLastName, Pageable page) {
		return repositoryClient.findClientsByNameAndPrimaryLastNameAndPage(name, primaryLastName, page);
	}

}
