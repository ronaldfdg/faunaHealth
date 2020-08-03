package com.faunahealth.web.service.Impl;

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
	public void save(Client client) throws Exception {
		repositoryClient.save(client);
	}

	@Override
	public void deleteById(int id) throws Exception {
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
	public Client findByDocumentNumberLike(String documentNumber) {
		Optional<Client> optional = repositoryClient.findByDocumentNumberLike(documentNumber);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	@Override
	public Client findByEmailAddressLike(String emailAddress) {
		Optional<Client> optional = repositoryClient.findByEmailAddressLike(emailAddress);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
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
	public Page<Client> findClientsByPage(String name, String primaryLastName, Pageable page){
		
		if (name != null && primaryLastName != null) {
			return repositoryClient.findClientsByNameAndPrimaryLastNameAndPage(name, primaryLastName, page);
		} else if (name != null) {
			return repositoryClient.findClientsByNameAndPage(name, page);
		} else if (primaryLastName != null) {
			return repositoryClient.findClientsByPrimaryLastNameAndPage(primaryLastName, page);
		} else {
			return null;
		}
		
	}

}
