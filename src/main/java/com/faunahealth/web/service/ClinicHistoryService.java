package com.faunahealth.web.service;

import com.faunahealth.web.entity.ClinicHistory;

public interface ClinicHistoryService {

	ClinicHistory findByPatient(int id);
	ClinicHistory findById(int id);
	boolean existsById(int id);
	void save(ClinicHistory clinicHistory);
	
}
