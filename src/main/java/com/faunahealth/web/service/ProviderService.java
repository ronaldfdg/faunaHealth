package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Provider;

public interface ProviderService {

	List<Provider> findAll();
	Provider findById(int id);
	boolean existsById(int id);
	void save(Provider provider);
}
