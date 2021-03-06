package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Provider;
import com.faunahealth.web.repository.ProviderRepository;
import com.faunahealth.web.service.ProviderService;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private ProviderRepository repositoryProvider;
	
	@Override
	public List<Provider> findAll() {
		return repositoryProvider.findAll(Sort.by("businessName").ascending());
	}

	@Override
	public void save(Provider provider) {
		repositoryProvider.save(provider);
	}

	@Override
	public Provider findById(int id) {
		Optional<Provider> optional = repositoryProvider.findById(id);
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public boolean existsById(int id) {
		return repositoryProvider.existsById(id);
	}

}
