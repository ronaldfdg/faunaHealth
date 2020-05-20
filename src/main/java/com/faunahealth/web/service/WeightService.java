package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import com.faunahealth.web.entity.Weight;

public interface WeightService {

	List<Weight> findByPatient_Id(int id);
	Weight findByPatientAndDate(int id, Date date);
	void save(Weight weight);
	void deleteById(int id);
	
}
