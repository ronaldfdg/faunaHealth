package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Breed;
import com.faunahealth.web.repository.BreedRepository;
import com.faunahealth.web.service.BreedService;

@Service
public class BreedServiceImpl implements BreedService {

	@Autowired
	private BreedRepository repositoryBreed;

	@Override
	public Breed findById(int id) {
		return repositoryBreed.findById(id);
	}
	
	@Override
	public List<Breed> getBreedsBySpecie(int specieId) {
		return repositoryBreed.getBreedsBySpecie(specieId);
	}
	
}
