package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Weight;
import com.faunahealth.web.repository.WeightRepository;
import com.faunahealth.web.service.WeightService;

@Service
public class WeightServiceImpl implements WeightService {

	@Autowired
	private WeightRepository repositoryWeight;
	
	@Override
	public List<Weight> findByPatient_Id(int id) {
		return repositoryWeight.getWeightsByPatient(id);
	}

	@Override
	public void save(Weight weight) {
		repositoryWeight.save(weight);
	}

	@Override
	public void deleteById(int id) {
		repositoryWeight.deleteById(id);
	}

	@Override
	public Weight findById(int id) {
		Optional<Weight> optional = repositoryWeight.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

}
