package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.OperationDetail;
import com.faunahealth.web.repository.OperationDetailRepository;
import com.faunahealth.web.service.OperationDetailService;

@Service
public class OperationDetailServiceImpl implements OperationDetailService {

	@Autowired
	private OperationDetailRepository repositoryOperationDetail;
	
	@Override
	public void save(OperationDetail operationDetail) {
		repositoryOperationDetail.save(operationDetail);
	}

	@Override
	public List<OperationDetail> findAll() {
		return repositoryOperationDetail.findOperations(Sort.by("operationDate").descending().and(Sort.by("patient.nickname").descending()));
	}

	@Override
	public List<OperationDetail> findOperationsByPatient(String nickname, String primaryLastName) {
		return repositoryOperationDetail.findOperationsByPatient(nickname, primaryLastName, Sort.by("operationDate").descending());
	}

	@Override
	public List<OperationDetail> findOperationsByPatientName(String nickname) {
		return repositoryOperationDetail.findOperationsByPatientName(nickname, Sort.by("operationDate").descending());
	}

	@Override
	public List<OperationDetail> findOperationsByPatientLastName(String primaryLastName) {
		return repositoryOperationDetail.findOperationsByPatientLastName(primaryLastName, Sort.by("operationDate").descending());
	}

	@Override
	public List<OperationDetail> findOperationsByDate(Date date) {
		return repositoryOperationDetail.findOperationsByDate(date, Sort.by("operationDate").descending());
	}

	@Override
	public List<OperationDetail> findOperationsBetweenDates(Date startDate, Date endDate) {
		return repositoryOperationDetail.findOperationsBetweenDates(startDate, endDate, Sort.by("operationDate").descending());
	}

}
