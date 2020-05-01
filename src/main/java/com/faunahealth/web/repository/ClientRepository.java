package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByDocumentNumber(String documentNumber);
	List<Client> findByNameContaining(String name);
	List<Client> findByPrimaryLastNameContaining(String primaryLastName);
	List<Client> findByNameContainingAndPrimaryLastNameContaining(String name, String primaryLastName);
	
}
