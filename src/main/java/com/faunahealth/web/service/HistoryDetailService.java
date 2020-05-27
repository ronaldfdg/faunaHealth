package com.faunahealth.web.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.HistoryDetail;

public interface HistoryDetailService {

	Page<HistoryDetail> findHistoryDetailsByClinicHistoryAndPage(int id, Pageable page);
	HistoryDetail findById(int id);
	HistoryDetail findHistoryPerClinicHistoryAndDate(int clinicHistoryId, Date date);
	boolean existsById(int id);
	void save(HistoryDetail historyDetail);
	
}
