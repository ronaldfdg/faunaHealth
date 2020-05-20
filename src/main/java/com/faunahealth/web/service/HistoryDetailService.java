package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import com.faunahealth.web.entity.HistoryDetail;

public interface HistoryDetailService {

	List<HistoryDetail> findHistoryDetailPerClinicHistory(int id);
	HistoryDetail findHistoryPerClinicHistoryAndDate(int clinicHistoryId, Date date);
	boolean existsById(int id);
	void save(HistoryDetail historyDetail);
	
}
