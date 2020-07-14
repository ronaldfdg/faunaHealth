package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Patient;
import com.faunahealth.web.repository.PatientRepository;
import com.faunahealth.web.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository repositoryPatient;
	
	@Override
	public void save(Patient patient) {
		repositoryPatient.save(patient);
	}

	@Override
	public List<Patient> getPatients() {
		return repositoryPatient.getPatients(Sort.by("nickname"));
	}

	@Override
	public Patient findById(int id) {
		Optional<Patient> optional = repositoryPatient.findById(id);
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}

	@Override
	public boolean existsById(int id) {
		return repositoryPatient.existsById(id);
	}

	@Override
	public void deleteById(int id) {
		repositoryPatient.deleteById(id);
	}

	@Override
	public List<Patient> getPatientsByClient(int clientId) {
		return repositoryPatient.getPatientsByClient(clientId, Sort.by("nickname"));
	}

	@Override
	public Page<Patient> findPatientsByNicknameAndPrimaryLastNameAndPage(String nickname, String primaryLastName,
			Pageable page) {
		
		if(nickname != null && primaryLastName != null) {
			return repositoryPatient.findPatientsByNicknameAndPrimaryLastNameAndPage(nickname, primaryLastName, page);
		} else if(nickname != null) {
			return repositoryPatient.findPatientsByNicknameAndPage(nickname, page);
		} else if(primaryLastName != null) {
			return repositoryPatient.findPatientsByPrimaryLastNameAndPage(primaryLastName, page);
		} else {
			return null;
		}
		
	}

	@Override
	public Page<Patient> findPatientsByClientAndPage(int clientId, Pageable page) {
		return repositoryPatient.findPatientsByClientAndPage(clientId, page);
	}

	@Override
	public List<Patient> findPatientsByIds(List<Integer> ids) {
		return repositoryPatient.findPatientsByIds(ids);
	}

}
