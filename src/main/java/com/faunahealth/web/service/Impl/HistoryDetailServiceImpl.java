package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.HistoryDetail;
import com.faunahealth.web.repository.HistoryDetailRepository;
import com.faunahealth.web.service.HistoryDetailService;

@Service
public class HistoryDetailServiceImpl implements HistoryDetailService{

	@Autowired
	private HistoryDetailRepository repositoryHistoryDetail;
	
	@Override
	public List<HistoryDetail> findHistoryDetailPerClinicHistory(int id) {
		return repositoryHistoryDetail.findHistoryDetailsPerClinicHistory(id);
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
	
}
