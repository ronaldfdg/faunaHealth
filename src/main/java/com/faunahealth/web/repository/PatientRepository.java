package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	@Query("select p from Patient p inner join fetch p.client")
	List<Patient> getPatients(Sort sort);
	
	@Query("select p from Patient p inner join fetch p.client where p.client.id = ?1")
	List<Patient> getPatientsByClient(int clientId, Sort sort);
	
	@Query("select p from Patient p inner join fetch p.client "
			+ "where p.nickname like concat('%', ?1, '%') and p.client.primaryLastName like concat('%', ?2, '%')")
	List<Patient> getPatientsByNicknameAndLastNameClient(String nickname, String primaryLastName);
	
	List<Patient> findByNicknameContaining(String nickname);
	List<Patient> findByClient_PrimaryLastNameContaining(String primaryLastName);
	
}
