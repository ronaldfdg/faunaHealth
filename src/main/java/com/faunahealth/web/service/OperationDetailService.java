package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import com.faunahealth.web.entity.OperationDetail;

public interface OperationDetailService {

	List<OperationDetail> findAll();
	List<OperationDetail> findOperationsByPatient(String nickname, String primaryLastName);
	List<OperationDetail> findOperationsByPatientName(String nickname);
	List<OperationDetail> findOperationsByPatientLastName(String primaryLastName);
	List<OperationDetail> findOperationsByDate(Date date);
	List<OperationDetail> findOperationsBetweenDates(Date startDate, Date endDate);
	void save(OperationDetail operationDetail);
	
}
