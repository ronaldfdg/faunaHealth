package com.faunahealth.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.OperationDetail;

public interface OperationDetailRepository extends JpaRepository<OperationDetail, Integer>{

	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation")
	List<OperationDetail> findOperations(Sort sort);
	
	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.nickname like concat('%', ?1, '%') "
				+ "and "
				+ "od.patient.client.primaryLastName like concat ('%', ?2, '%')")
	List<OperationDetail> findOperationsByPatient(String nickname, String primaryLastName, Sort sort);
	
	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.nickname like concat('%', ?1, '%')")
	List<OperationDetail> findOperationsByPatientName(String nickname, Sort sort);
	
	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.patient.client.primaryLastName like concat ('%', ?1, '%')")
	List<OperationDetail> findOperationsByPatientLastName(String primaryLastName, Sort sort);
	
	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.operationDate = ?1")
	List<OperationDetail> findOperationsByDate(Date date, Sort sort);
	
	@Query("select od from OperationDetail od "
			+ "inner join fetch od.patient "
			+ "inner join fetch od.user "
			+ "inner join fetch od.operation "
				+ "where "
				+ "od.operationDate between ?1 and ?2")
	List<OperationDetail> findOperationsBetweenDates(Date startDate, Date endDate, Sort sort);
	
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
