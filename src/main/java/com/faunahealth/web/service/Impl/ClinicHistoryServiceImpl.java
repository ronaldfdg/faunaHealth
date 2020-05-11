package com.faunahealth.web.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.ClinicHistory;
import com.faunahealth.web.repository.ClinicHistoryRepository;
import com.faunahealth.web.service.ClinicHistoryService;

@Service
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

	@Autowired
	private ClinicHistoryRepository repositoryClinicHis;
	
	@Override
	public void save(ClinicHistory clinicHistory) {
		repositoryClinicHis.save(clinicHistory);
	}

	@Override
	public ClinicHistory findByPatient(int id) {
		Optional<ClinicHistory> optional = repositoryClinicHis.findByPatient_Id(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public ClinicHistory findById(int id) {
		Optional<ClinicHistory> optional = repositoryClinicHis.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public boolean existsById(int id) {
		return repositoryClinicHis.existsById(id);
	}

}
