package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Provider;

public interface ProviderService {

	List<Provider> findAll();
	void save(Provider provider);
}
