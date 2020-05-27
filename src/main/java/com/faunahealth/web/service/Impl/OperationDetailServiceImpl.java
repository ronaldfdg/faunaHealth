package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<OperationDetail> findOperationsByPatient(String nickname, String primaryLastName, Pageable page) {
		return repositoryOperationDetail.findOperationsByPatient(nickname, primaryLastName, page);
	}

	@Override
	public Page<OperationDetail> findOperationsByPatientName(String nickname, Pageable page) {
		return repositoryOperationDetail.findOperationsByPatientName(nickname, page);
	}

	@Override
	public Page<OperationDetail> findOperationsByPatientLastName(String primaryLastName, Pageable page) {
		return repositoryOperationDetail.findOperationsByPatientLastName(primaryLastName, page);
	}

	@Override
	public Page<OperationDetail> findOperationsByDate(Date date, Pageable page) {
		return repositoryOperationDetail.findOperationsByDate(date, page);
	}

	@Override
	public Page<OperationDetail> findOperationsBetweenDates(Date startDate, Date endDate, Pageable page) {
		return repositoryOperationDetail.findOperationsBetweenDates(startDate, endDate, page);
	}

	@Override
	public OperationDetail findById(int id) {
		Optional<OperationDetail> optional = repositoryOperationDetail.findById(id);
		if(optional.isPresent())
			return optional.get();
		return null;
	}

}
