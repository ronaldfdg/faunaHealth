package com.faunahealth.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.OperationDetail;

public interface OperationDetailRepository extends JpaRepository<OperationDetail, Integer>{

	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation")
	List<OperationDetail> findOperations(Sort sort);
	
	@Query(value = "select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.nickname like concat('%', :nickname, '%') "
				+ "and "
				+ "od.patient.client.primaryLastName like concat ('%', :primaryLastName, '%')"
				, countQuery = "select count (od.id) from OperationDetail od")
	Page<OperationDetail> findOperationsByPatient(@Param("nickname") String nickname, @Param("primaryLastName") String primaryLastName, Pageable page);
	
	@Query(value = "select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.nickname like concat('%', :nickname, '%')"
				, countQuery = "select count (od.id) from OperationDetail od")
	Page<OperationDetail> findOperationsByPatientName(@Param("nickname") String nickname, Pageable page);
	
	@Query(value = "select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.client.primaryLastName like concat ('%', :primaryLastName, '%')"
				, countQuery = "select count (od.id) from OperationDetail od")
	Page<OperationDetail> findOperationsByPatientLastName(@Param("primaryLastName") String primaryLastName, Pageable page);
	
	@Query(value = "select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.operationDate = :date"
				, countQuery = "select count (od.id) from OperationDetail od")
	Page<OperationDetail> findOperationsByDate(@Param("date") Date date, Pageable page);
	
	@Query(value = "select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.operationDate between :startDate and :endDate"
				, countQuery = "select count (od.id) from OperationDetail od")
	Page<OperationDetail> findOperationsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable page);
	
	/*@Query("select od from OperationDetail od "
		+ "inner join fetch od.patient "
		+ "inner join fetch od.user "
		+ "inner join fetch od.operation "
			+ "where "
			+ "od.patient.nickname like concat('%', ?1, '%') "
			+ "and "
			+ "od.patient.client.primaryLastName like concat ('%', ?2, '%') "
			+ "and "
			+ "od.operationDate = ?3")
	List<OperationDetail> findOperationsByPatientAndDate(String nickname, String primaryLastName, Date date, Sort sort);*/
	
	/*@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.client.primaryLastName like concat('%', ?1, '%') "
				+ "and "
				+ "od.operationDate = ?2")
	List<OperationDetail> findOperationsByPatientLastNameAndDate(String lastName, Date date, Sort sort);*/
	
	/*@Query("select od from OperationDetail od "
		+ "inner join fetch od.patient "
		+ "inner join fetch od.user "
		+ "inner join fetch od.operation "
			+ "where "
			+ "od.patient.nickname like concat('%', ?1, '%') "
			+ "and "
			+ "od.patient.client.primaryLastName like concat ('%', ?2, '%') "
			+ "and "
			+ "od.operationDate between ?3 and ?4")
	List<OperationDetail> findOperationsByPatientAndDates(String nickname, String primaryLastName, Date startDate, Date endDate, Sort sort);*/
}
