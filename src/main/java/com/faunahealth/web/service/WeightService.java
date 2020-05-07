package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Weight;

public interface WeightService {

	List<Weight> findByPatient_Id(int id);
	Weight findById(int id);
	void save(Weight weight);
	void deleteById(int id);
	
}
