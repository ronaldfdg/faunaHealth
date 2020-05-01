package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.District;
import com.faunahealth.web.repository.DistrictRepository;
import com.faunahealth.web.service.DistrictService;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository repositoryDistrict;
	
	@Override
	public List<District> findAll() {
		return repositoryDistrict.findAll(Sort.by("name"));
	}

}
