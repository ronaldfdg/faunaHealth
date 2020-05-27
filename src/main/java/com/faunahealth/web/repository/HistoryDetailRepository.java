package com.faunahealth.web.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.HistoryDetail;

public interface HistoryDetailRepository extends JpaRepository<HistoryDetail, Integer>{

	@Query(value = "select h from HistoryDetail h "
			+ "inner join fetch h.clinicHistory "
			+ "where h.clinicHistory.id = :id"
			, countQuery = "select count (h.id) from HistoryDetail h")
	Page<HistoryDetail> findHistoryDetailsByClinicHistoryAndPage(@Param("id") int id, Pageable page);
	
	/*@Query("select h from HistoryDetail h "
			+ "inner join fetch h.clinicHistory "
			+ "where h.clinicHistory.id = ?1")
	List<HistoryDetail> findHistoryDetailsPerClinicHistory(int id, Sort sort);*/
	
	@Query("select h from HistoryDetail h "
			+ "inner join fetch h.clinicHistory "
			+ "where h.clinicHistory.id = ?1 "
			+ "and h.attentionDate = ?2")
	Optional<HistoryDetail> findHistoryDetailPerClinicHistoryAndDate(int clinicHistoryId, Date date);
}
