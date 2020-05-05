package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Breed;

public interface BreedService {

	List<Breed> getBreedsDog();
	List<Breed> getBreedsCat();
	List<Breed> getBreedsRabbit();
	
}
