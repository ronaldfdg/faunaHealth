package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.HistoryDetail;
import com.faunahealth.web.repository.HistoryDetailRepository;
import com.faunahealth.web.service.HistoryDetailService;

@Service
public class HistoryDetailServiceImpl implements HistoryDetailService{

	@Autowired
	private HistoryDetailRepository repositoryHistoryDetail;
	
	@Override
	public Page<HistoryDetail> findHistoryDetailsByClinicHistoryAndPage(int id, Pageable page) {
		return repositoryHistoryDetail.findHistoryDetailsByClinicHistoryAndPage(id, page);
	}

	@Override
	public void save(HistoryDetail historyDetail) {
		repositoryHistoryDetail.save(historyDetail);
	}

	@Override
	public HistoryDetail findHistoryPerClinicHistoryAndDate(int clinicHistoryId, Date date) {
		Optional<HistoryDetail> optional = repositoryHistoryDetail.findHistoryDetailPerClinicHistoryAndDate(clinicHistoryId, date);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public boolean existsById(int id) {
		return repositoryHistoryDetail.existsById(id);
	}

	@Override
	public HistoryDetail findById(int id) {
		Optional<HistoryDetail> optional = repositoryHistoryDetail.findById(id);
		if(optional.isPresent())
			return optional.get();
		return null;
	}
	
}
