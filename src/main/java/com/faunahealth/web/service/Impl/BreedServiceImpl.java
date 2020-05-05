package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Breed;
import com.faunahealth.web.repository.BreedRepository;
import com.faunahealth.web.service.BreedService;

@Service
public class BreedServiceImpl implements BreedService {

	@Autowired
	private BreedRepository repositoryBreed;

	@Override
	public List<Breed> getBreedsDog() {
		return repositoryBreed.getBreedsDog(Sort.by("name"));
	}

	@Override
	public List<Breed> getBreedsCat() {
		return repositoryBreed.getBreedsCat(Sort.by("name"));
	}

	@Override
	public List<Breed> getBreedsRabbit() {
		return repositoryBreed.getBreedsRabbit(Sort.by("name"));
	}
	
}
