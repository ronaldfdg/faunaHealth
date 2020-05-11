package com.faunahealth.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.ClinicHistory;

public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Integer> {

	Optional<ClinicHistory> findByPatient_Id(int id);
	
}
