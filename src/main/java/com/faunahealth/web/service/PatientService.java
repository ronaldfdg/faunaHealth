package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Patient;

public interface PatientService {

	List<Patient> getPatients();
	List<Patient> getPatientsByClient(int clientId);
	List<Patient> getPatientsByNicknameAndLastNameClient(String nickname, String primaryLastName);
	List<Patient> findByNicknameContaining(String nickname);
	List<Patient> findByClient_PrimaryLastNameContaining(String primaryLastName);
	Patient findById(int id);
	boolean existsById(int id);
	void save(Patient patient);
	void deleteById(int id);
}
