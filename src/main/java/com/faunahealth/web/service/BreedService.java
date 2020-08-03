package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Breed;

public interface BreedService {

	List<Breed> getBreedsBySpecie(int specieId);
	Breed findById(int id);
	
}
