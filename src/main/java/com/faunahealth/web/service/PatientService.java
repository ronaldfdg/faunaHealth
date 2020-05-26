package com.faunahealth.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Patient;

public interface PatientService {

	List<Patient> getPatients();
	
	Page<Patient> findPatientsByClientAndPage(int clientId, Pageable page);
	List<Patient> getPatientsByClient(int clientId);
	
	Page<Patient> findPatientsByNicknameAndPrimaryLastNameAndPage(String nickname, String primaryLastName, Pageable page);
	List<Patient> getPatientsByNicknameAndLastNameClient(String nickname, String primaryLastName);
	
	Page<Patient> findPatientsByNicknameAndPage(String nickname, Pageable page);
	List<Patient> findByNicknameContaining(String nickname);
	
	Page<Patient> findPatientsByPrimaryLastName(String primaryLastName, Pageable page);
	List<Patient> findByClient_PrimaryLastNameContaining(String primaryLastName);
	
	Patient findById(int id);
	boolean existsById(int id);
	void save(Patient patient);
	void deleteById(int id);
}
