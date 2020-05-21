package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Operation;

public interface OperationService {

	List<Operation> findAll();
	
}
