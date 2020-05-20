package com.faunahealth.web.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.HistoryDetail;

public interface HistoryDetailRepository extends JpaRepository<HistoryDetail, Integer>{

	@Query("select h from HistoryDetail h "
			+ "inner join fetch h.clinicHistory "
			+ "where h.clinicHistory.id = ?1")
	List<HistoryDetail> findHistoryDetailsPerClinicHistory(int id);
	
	@Query("select h from HistoryDetail h "
			+ "inner join fetch h.clinicHistory "
			+ "where h.clinicHistory.id = ?1 "
			+ "and h.attentionDate = ?2")
	Optional<HistoryDetail> findHistoryDetailPerClinicHistoryAndDate(int clinicHistoryId, Date date);
}
