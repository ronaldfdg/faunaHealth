package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Operation;
import com.faunahealth.web.repository.OperationRepository;
import com.faunahealth.web.service.OperationService;

@Service
public class OperationServiceImpl implements OperationService{

	@Autowired
	OperationRepository repositoryOperation;
	
	@Override
	public List<Operation> findAll() {
		return repositoryOperation.findAll(Sort.by("name"));
	}

}
