package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.OperationDetail;

public interface OperationDetailService {

	List<OperationDetail> findAll();
	Page<OperationDetail> findOperationsByPatient(String nickname, String primaryLastName, Pageable page);
	Page<OperationDetail> findOperationsByPatientName(String nickname, Pageable page);
	Page<OperationDetail> findOperationsByPatientLastName(String primaryLastName, Pageable page);
	Page<OperationDetail> findOperationsByDate(Date date, Pageable page);
	Page<OperationDetail> findOperationsBetweenDates(Date startDate, Date endDate, Pageable page);
	void save(OperationDetail operationDetail);
	
}
